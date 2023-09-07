package com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh;

import com.nppgks.reportingsystem.constants.ManualReportTypes;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.formulas.MI3272Formulas;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KmhMassmByPuReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;
    public KmhMassmByPuReportGenerator(@Qualifier("saveManyTimesADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualTagService manualTagService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualTagService = manualTagService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        List<ReportData> reportDataList = new ArrayList<>();
        List<ManualTagForOpc> tags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypes.KMH_MASSM_BY_PU.name());
        List<String> tagAddresses = DataConverter.convertTagsToListOfAddresses(tags);
        Map<String, String> tagValuesFromOpc = opcServiceRequests.getTagValuesFromOpc(tagAddresses);
        Map<String, ManualTagForOpc> addressToTagMap = DataConverter.convertTagListToMapWithAddressKey(tags);
        Map<String, String> permanentNameToAddressMap = DataConverter.createPermanentNameToAddressMap(tags);

        for(Map.Entry<String, String> addressToValue: tagValuesFromOpc.entrySet()){
            reportDataList.add(new ReportData(
                    null,
                    addressToValue.getValue(),
                    ManualTagForOpc.toTag(addressToTagMap.get(addressToValue.getKey())),
                    report
            ));
        }
        calculateAndFillInWithFinalData(reportDataList, tagValuesFromOpc, permanentNameToAddressMap);

        return reportDataList;
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        return new Report(
                null,
                "КМХ контрольного МПР с помощью ПУ "+ SingleDateTimeFormatter.formatToSinglePattern(currentDt),
                currentDt,
                ManualReportTypes.KMH_MASSM_BY_MASSM.name()
        );
    }

    private void calculateAndFillInWithFinalData(List<ReportData> reportDataList,
                                                 Map<String, String> tagValuesFromOpc,
                                                 Map<String, String> permanentNameToAddressMap){
        String V_KP_0Str = tagValuesFromOpc.get(permanentNameToAddressMap.get("V_KP_0"));
        double V_KP_0 = Double.parseDouble(V_KP_0Str);

        String alpha_cyl_tStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("alpha_cyl_t"));
        Double alpha_cyl_t = parseDoubleObject(alpha_cyl_tStr);
        String alpha_cyl_t_sqStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("alpha_cyl_t_sq"));
        double alpha_cyl_t_sq = Double.parseDouble(alpha_cyl_t_sqStr);

        String t_KP_ijStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("t_KP_ij_avg"));
        double[][] t_KP_ij_avg = ArrayParser.to2DArray(t_KP_ijStr);
        String alpha_st_tStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("alpha_st_t"));
        double alpha_st_t = Double.parseDouble(alpha_st_tStr);
        String t_st_ijStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("t_st_ij"));
        double[][] t_st_ij = ArrayParser.to2DArray(t_st_ijStr);
        String DStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("D"));
        double D = Double.parseDouble(DStr);
        String EStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("E"));
        double E = Double.parseDouble(EStr);
        String sStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("s"));
        double s = Double.parseDouble(sStr);
        String P_KP_ijStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("P_KP_ij_avg"));
        double[][] P_KP_ij_avg = ArrayParser.to2DArray(P_KP_ijStr);

        alpha_cyl_t = MI3272Formulas.calculateAlpha_cyl_t(alpha_cyl_t, alpha_cyl_t_sq);
        double[][] V_KP_pr_ij = MI3272Formulas.calculateV_KP_pr_ij_Formula4(V_KP_0, alpha_cyl_t, t_KP_ij_avg, alpha_st_t, t_st_ij, D, E, s, P_KP_ij_avg);
        String N_TPR_ij_avgStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("N_TPR_ij_avg"));
        double[][] N_TPR_ij_avg = ArrayParser.to2DArray(N_TPR_ij_avgStr);
        double[][] K_TPR_ij = MI3272Formulas.calculateK_TPR_ij(N_TPR_ij_avg, V_KP_pr_ij);
        double[] Pi_j = MI3272Formulas.calculatePi_j(K_TPR_ij);
        double[] K_TPR_j = MI3272Formulas.calculateK_TPR_j(K_TPR_ij);

        String delta_MStr = tagValuesFromOpc.get(permanentNameToAddressMap.get("delta_M"));
        double[][] delta_M = ArrayParser.to2DArray(delta_MStr);

        double delta_max = Arrays.stream(delta_M)
                .flatMapToDouble(Arrays::stream)
                .map(Math::abs)
                .max().orElseThrow();

        String conclusion = "годен";

        if(delta_max >= 0.2) conclusion = "не годен";

        Tag V_KP_pr_ijTag = manualTagService.getTagByNameAndReportType("V_KP_pr_ij", ManualReportTypes.KMH_MASSM_BY_PU.name());
        Tag K_TPR_ijTag = manualTagService.getTagByNameAndReportType("K_TPR_ij", ManualReportTypes.KMH_MASSM_BY_PU.name());
        Tag Pi_jTag = manualTagService.getTagByNameAndReportType("Pi_j", ManualReportTypes.KMH_MASSM_BY_PU.name());
        Tag K_TPR_jTag = manualTagService.getTagByNameAndReportType("K_TPR_j", ManualReportTypes.KMH_MASSM_BY_PU.name());
        Tag delta_maxTag = manualTagService.getTagByNameAndReportType("delta_max", ManualReportTypes.KMH_MASSM_BY_PU.name());
        Tag conclusionTag = manualTagService.getTagByNameAndReportType("conclusion", ManualReportTypes.KMH_MASSM_BY_PU.name());

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(delta_max),
                delta_maxTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                conclusion,
                conclusionTag,
                report
        ));
        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(V_KP_pr_ij),
                V_KP_pr_ijTag,
                report
        ));
        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(K_TPR_ij),
                K_TPR_ijTag,
                report
        ));
        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(Pi_j),
                Pi_jTag,
                report
        ));
        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(K_TPR_j),
                K_TPR_jTag,
                report
        ));
    }

    private Double parseDoubleObject(String str){
        if(str != null){
            return Double.parseDouble(str);
        }
        else return null;
    }
}
