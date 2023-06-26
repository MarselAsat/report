package com.nppgks.reportingsystem.reportgeneration.acts;

import com.nppgks.reportingsystem.constants.CalcMethod;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.calculations.entity.Report;
import com.nppgks.reportingsystem.db.calculations.entity.ReportData;
import com.nppgks.reportingsystem.db.calculations.entity.Tag;
import com.nppgks.reportingsystem.dto.calc.CalcTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.service.dbservices.SettingsService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcReportService;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter.formatToSinglePattern;

@Service
@RequiredArgsConstructor
public class ActOfAcceptanceGenerator {
    private final CalcReportService reportService;

    private final SettingsService settingsService;
    private final ActOfAcceptanceDbService actOfAcceptanceDbService;
    private final CalcTagService tagService;
    private final OpcServiceRequests opcServiceRequests;
    private List<ReportData> reportDataList = new ArrayList<>();
    private Report report;
    private boolean isSaved = false;

    public List<ReportData> generateActOfAcceptance() {
        LocalDateTime currentDt = LocalDateTime.now();
        report = new Report(
                null,
                "Акт приема-сдачи нефти от %s".formatted(formatToSinglePattern(currentDt)),
                currentDt,
                CalcMethod.ACCEPTANCE_ACT.name());

        List<CalcTagForOpc> tags = tagService.getTagsByInitialAndCalcMethod(true, CalcMethod.ACCEPTANCE_ACT.name());
        List<String> tagAddressesForOpc = tags.stream()
                .map(CalcTagForOpc::address)
                .toList();
        Map<String, String> tagValuesFromOpc = opcServiceRequests.getTagValuesFromOpc(tagAddressesForOpc);
        reportDataList = convertTagValuesToReportData(tagValuesFromOpc, tags, report);
        return reportDataList;
    }

    public String saveInDb() {
        if (isSaved) {
            return "Эти результаты уже сохранены в базу данных";
        }
        String response = actOfAcceptanceDbService.saveReportData(reportDataList, report);
        isSaved = true;
        return response;
    }

    private int computeNumOfShiftColumns(LocalDateTime currentDt) {
        LinkedHashMap<String, String> shiftNumToStartTime = settingsService.getMapValuesBySettingName(SettingsConstants.START_SHIFT_REPORT);
        int shiftsCount = shiftNumToStartTime.size();
        LocalTime shiftTimePrev = LocalTime.parse(shiftNumToStartTime.get("1"));
        int currentShift = getCurrentShift(currentDt.toLocalTime(), shiftNumToStartTime);
        return 0;
    }

    public static int getCurrentShift(LocalTime currentTime, LinkedHashMap<String, String> shiftNumToStartTime){
        int shiftsCount = shiftNumToStartTime.size();
        LocalTime shiftTimePrev = LocalTime.parse(shiftNumToStartTime.get("1"));
        int currentShift = 1;
        for (int i = 2; i <= shiftsCount; i++) {
            String shiftNumStr = i+"";
            LocalTime shiftTime = LocalTime.parse(shiftNumToStartTime.get(shiftNumStr));
            if (currentTime.isAfter(shiftTimePrev) && currentTime.isBefore(shiftTime)) {
                currentShift = i - 1;
                break;
            }
            if(i == shiftsCount && currentTime.isAfter(shiftTime)){
                currentShift = i;
            }
            shiftTimePrev = shiftTime;
        }
        return currentShift;
    }

    private List<ReportData> convertTagValuesToReportData(Map<String, String> tagValuesFromOpc, List<CalcTagForOpc> tags, Report report) {
        Map<String, Tag> addressToTag = tags.stream()
                .map(t -> Map.entry(t.address(), CalcTagForOpc.toTag(t)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));

        List<ReportData> reportDataList = new ArrayList<>();
        for (Map.Entry<String, String> tagAddressToValue : tagValuesFromOpc.entrySet()) {
            reportDataList.add(
                    new ReportData(
                            null,
                            tagAddressToValue.getValue(),
                            addressToTag.get(tagAddressToValue.getKey()),
                            report));
        }
        return reportDataList;
    }
}
