package com.nppgks.reportingsystem.reportgeneration.acts;

import com.nppgks.reportingsystem.constants.ManualReportTypes;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.service.dbservices.SettingsService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.ArrayParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter.formatToSinglePattern;

@Service
@RequiredArgsConstructor
public class AcceptanceActGenerator {

    private final String SHIFT_START_DT_TAGNAME = "dtStart_shiftn";
    private final String SHIFT_END_DT_TAGNAME = "dtEnd_shiftn";
    private final String TAG_NAME_POSTFIX = "shiftn";
    private final String TAG_ADDRESS_POSTFIX = "shift\\d";
    private final SettingsService settingsService;
    private final AcceptanceActDbService acceptanceActDbService;
    private final ManualTagService tagService;
    private final OpcServiceRequests opcServiceRequests;
    private List<ReportData> reportDataList = new ArrayList<>();
    private List<ManualTagForOpc> tags;
    private Report report;
    private boolean isSaved = false;

    public List<ReportData> generateActOfAcceptance() {
        LocalDateTime currentDt = LocalDateTime.now();
        report = new Report(
                null,
                "Акт приема-сдачи нефти от %s".formatted(formatToSinglePattern(currentDt)),
                currentDt,
                ManualReportTypes.ACCEPTANCE_ACT.name());

        tags = tagService.getTagsByInitialAndReportType(true, ManualReportTypes.ACCEPTANCE_ACT.name());
        List<String> tagAddressesForOpc = new ArrayList<>();
        int numOfShiftColumns = computeNumOfShiftColumns(currentDt);
        for (ManualTagForOpc tag : tags) {
            if (tag.address().matches(".*" + TAG_NAME_POSTFIX)) {
                for (int i = 1; i <= numOfShiftColumns; i++) {
                    tagAddressesForOpc.add(tag.address().replace(TAG_NAME_POSTFIX, "shift" + i));
                }
            } else tagAddressesForOpc.add(tag.address());
        }

        Map<String, String> tagValuesFromOpc = opcServiceRequests.getTagValuesFromOpc(tagAddressesForOpc);
        reportDataList = convertTagValuesToReportData(tagValuesFromOpc, tags, report);
        return reportDataList;
    }

    public void updateShiftsDateTimeInReportData(List<String> dtStartShifts, List<String> dtEndShifts) {
        boolean startDtExists = false;
        boolean endDtExists = false;
        String dtStartShiftsJson = ArrayParser.fromObjectToJson(dtStartShifts);
        String dtEndShiftsJson = ArrayParser.fromObjectToJson(dtEndShifts);
        for (ReportData rd : reportDataList) {
            if (rd.getTag().getPermanentName().equals(SHIFT_START_DT_TAGNAME)) {
                if(!rd.getData().equals(dtStartShiftsJson)){
                    isSaved = false;
                    rd.setData(dtStartShiftsJson);
                }
                startDtExists = true;
            }
            if (rd.getTag().getPermanentName().equals(SHIFT_END_DT_TAGNAME)) {
                if(!rd.getData().equals(dtEndShiftsJson)){
                    isSaved = false;
                    rd.setData(dtEndShiftsJson);
                }
                endDtExists = true;
            }
        }

        Tag shiftStartDtTag = null;
        Tag shiftEndDtTag = null;

        for (ManualTagForOpc t : tags) {
            if (t.permanentName().equals(SHIFT_START_DT_TAGNAME)) {
                shiftStartDtTag = ManualTagForOpc.toTag(t);
            }
            if (t.permanentName().equals(SHIFT_END_DT_TAGNAME)) {
                shiftEndDtTag = ManualTagForOpc.toTag(t);
            }
        }

        if (!startDtExists) {
            reportDataList.add(new ReportData(
                    null,
                    dtStartShiftsJson,
                    shiftStartDtTag,
                    report));
        }
        if (!endDtExists) {
            reportDataList.add(new ReportData(
                    null,
                    dtEndShiftsJson,
                    shiftEndDtTag,
                    report));
        }
    }

    public String saveInDb() {
        if (isSaved) {
            return "Эти результаты уже сохранены в базу данных";
        }
        String response = acceptanceActDbService.saveReportData(reportDataList, report);
        isSaved = true;
        return response;
    }


    private int computeNumOfShiftColumns(LocalDateTime currentDt) {
        LinkedHashMap<String, String> shiftNumToStartTime = settingsService.getMapValuesBySettingName(SettingsConstants.START_SHIFT_REPORT);
        int shiftCount = shiftNumToStartTime.size();
        int currentShift = getCurrentShift(currentDt.toLocalTime(), shiftNumToStartTime);
        return currentShift == 1 ? shiftCount : currentShift - 1;
    }

    public static int getCurrentShift(LocalTime currentTime, LinkedHashMap<String, String> shiftNumToStartTime) {
        Comparator<String> byTime =
                (String time1Str, String time2Str) -> {
                    LocalTime time1 = LocalTime.parse(time1Str);
                    LocalTime time2 = LocalTime.parse(time2Str);
                    return time1.compareTo(time2);
                };

        List<Map.Entry<String, String>> shiftNumToStartTimeSorted = shiftNumToStartTime.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(byTime)).toList();

        int shiftsCount = shiftNumToStartTime.size();
        LocalTime shiftTimePrev = LocalTime.parse(shiftNumToStartTimeSorted.get(0).getValue());
        int currentShift = Integer.parseInt(shiftNumToStartTimeSorted.get(0).getKey());
        int shiftNumPrev = currentShift;
        for (int i = 1; i < shiftsCount; i++) {
            LocalTime shiftTime = LocalTime.parse(shiftNumToStartTimeSorted.get(i).getValue());
            int shiftNum = Integer.parseInt(shiftNumToStartTimeSorted.get(i).getKey());
            if (currentTime.isAfter(shiftTimePrev) && currentTime.isBefore(shiftTime)) {
                currentShift = shiftNumPrev;
                break;
            }
            if ((shiftNum + "").equals(shiftNumToStartTimeSorted.get(shiftsCount - 1).getKey())) {
                currentShift = shiftNum;
            }
            shiftTimePrev = shiftTime;
            shiftNumPrev = shiftNum;
        }
        return currentShift;
    }

    private List<ReportData> convertTagValuesToReportData(Map<String, String> tagValuesFromOpc, List<ManualTagForOpc> tags, Report report) {
        Map<String, Tag> addressToTag = tags.stream()
                .map(t -> Map.entry(t.address(), ManualTagForOpc.toTag(t)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));

        Map<Tag, List<String>> tagToMultipleValues = new HashMap<>();
        List<ReportData> reportDataList = new ArrayList<>();
        for (Map.Entry<String, String> tagAddressToValue : tagValuesFromOpc.entrySet()) {
            String tagAddress = tagAddressToValue.getKey();
            String tagValue = tagAddressToValue.getValue();
            if (tagAddress.matches(".*" + TAG_ADDRESS_POSTFIX)) {
                Tag tagForMultipleValues = addressToTag.get(tagAddress.replaceAll(TAG_ADDRESS_POSTFIX, TAG_NAME_POSTFIX));
                if (tagToMultipleValues.containsKey(tagForMultipleValues)) {
                    tagToMultipleValues.get(tagForMultipleValues).add(tagValue);
                } else {
                    List<String> values = new ArrayList<>();
                    values.add(tagValue);
                    tagToMultipleValues.put(tagForMultipleValues, values);
                }
            } else {
                reportDataList.add(
                        new ReportData(
                                null,
                                tagAddressToValue.getValue(),
                                addressToTag.get(tagAddressToValue.getKey()),
                                report));
            }
        }

        for (Map.Entry<Tag, List<String>> entry : tagToMultipleValues.entrySet()) {
            String values = ArrayParser.fromObjectToJson(entry.getValue());
            reportDataList.add(
                    new ReportData(
                            null,
                            values,
                            entry.getKey(),
                            report));
        }

        return reportDataList;
    }
}
