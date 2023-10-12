package com.nppgks.reportingsystem.reportgeneration.manual_reports.kmh;

import com.nppgks.reportingsystem.constants.ManualReportTypesEnum;
import com.nppgks.reportingsystem.db.manual_reports.entity.Report;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.DataConverter;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.ManualReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import com.nppgks.reportingsystem.util.ArrayParser;
import com.nppgks.reportingsystem.util.DataRounder;
import com.nppgks.reportingsystem.util.time.SingleDateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KmhDensityMeterReportGenerator extends ManualReportGenerator {

    private final OpcServiceRequests opcServiceRequests;
    private final ManualTagService manualTagService;
    private final ManualReportTypeService manualReportTypeService;

    public KmhDensityMeterReportGenerator(@Qualifier("saveManyTimesADayStrategy") SaveReportStrategy saveReportStrategy, OpcServiceRequests opcServiceRequests, ManualTagService manualTagService, ManualReportTypeService manualReportTypeService) {
        super(saveReportStrategy);
        this.opcServiceRequests = opcServiceRequests;
        this.manualTagService = manualTagService;
        this.manualReportTypeService = manualReportTypeService;
    }

    @Override
    protected List<ReportData> generateReportDataList() {
        List<ReportData> reportDataList = new ArrayList<>();
        List<ManualTagForOpc> tags = manualTagService.getTagsByInitialAndReportType(true, ManualReportTypesEnum.kmhDensityMeter.name());
        List<String> tagAddresses = DataConverter.convertTagsToListOfAddresses(tags);
        Map<String, String> tagValuesFromOpc = opcServiceRequests.getTagValuesFromOpc(tagAddresses);
        Map<String, ManualTagForOpc> addressToTagMap = DataConverter.convertTagListToMapWithAddressKey(tags);
        Map<String, String> permanentNameToAddressMap = DataConverter.createPermanentNameToAddressMap(tags);

        for (Map.Entry<String, String> addressToValue : tagValuesFromOpc.entrySet()) {
            reportDataList.add(new ReportData(
                    null,
                    addressToValue.getValue(),
                    ManualTagForOpc.toTag(addressToTagMap.get(addressToValue.getKey())),
                    report
            ));
        }

        String workingOrReserve = tagValuesFromOpc.get(permanentNameToAddressMap.get("workingOrReserve"));
        String workingOrReserveGenitive = switch(workingOrReserve.toLowerCase()){
            case "рабочий" -> "рабочего";
            case "резервный" -> "резервного";
            default -> throw new RuntimeException("Тэг workingOrReserve должен иметь одно из значений: рабочий или резервный");
        };

        report.setName(String.format("КМХ %s ПП по ареометру от %s ", workingOrReserveGenitive, SingleDateTimeFormatter.formatToSinglePattern(report.getCreationDt())));

        String rho_meas_i_str = tagValuesFromOpc.get(permanentNameToAddressMap.get("rho_meas_i"));
        double[] rho_meas_i = ArrayParser.toArray(rho_meas_i_str);
        String t_ar_i_str = tagValuesFromOpc.get(permanentNameToAddressMap.get("t_ar_i"));
        double[] t_ar_i = ArrayParser.toArray(t_ar_i_str);
        String P_pl_i_str = tagValuesFromOpc.get(permanentNameToAddressMap.get("P_pl_i"));
        double[] P_pl_i = ArrayParser.toArray(P_pl_i_str);
        String t_pl_i_str = tagValuesFromOpc.get(permanentNameToAddressMap.get("t_pl_i"));
        double[] t_pl_i = ArrayParser.toArray(t_pl_i_str);
        String rho_pl_i_str = tagValuesFromOpc.get(permanentNameToAddressMap.get("rho_pl_i"));
        double[] rho_pl_i = ArrayParser.toArray(rho_pl_i_str);
        String delta_syst_str = tagValuesFromOpc.get(permanentNameToAddressMap.get("delta_syst"));
        double delta_syst = Double.parseDouble(delta_syst_str);
        String delta_met_str = tagValuesFromOpc.get(permanentNameToAddressMap.get("delta_met"));
        double delta_met = Double.parseDouble(delta_met_str);

        int len = rho_meas_i.length;
        double[] rho15 = new double[len];
        int lenDiv2 = len/2;
        double[] rho_lpr_i = new double[lenDiv2];
        double[] beta15 = new double[lenDiv2];
        double[] gamma_pl_i = new double[lenDiv2];
        double[] delta_pk_i = new double[lenDiv2];

        for (int i = 0; i < len; i++) {
            rho15[i] = calculateRho15(rho_meas_i[i], t_ar_i[i]);
            if(i%2!=0){
                double[] rho_betta_gamma = calculateRho_l_pr_and_beta_and_gamma(P_pl_i[i/2], t_pl_i[i/2], rho15[i-1], rho15[i], delta_syst);
                rho_lpr_i[i/2] = rho_betta_gamma[0];
                beta15[i/2] = rho_betta_gamma[1];
                gamma_pl_i[i/2] = rho_betta_gamma[2];
                delta_pk_i[i/2] = Math.abs(rho_pl_i[i/2] - rho_betta_gamma[0]);
            }
        }

        double delta_max = Arrays.stream(delta_pk_i).max().orElseThrow();

        DataRounder.roundDoubleArray(delta_pk_i);
        DataRounder.roundDoubleArray(rho_lpr_i);
        DataRounder.roundDoubleArray(beta15);
        DataRounder.roundDoubleArray(gamma_pl_i);

        String conclusion = "годен";

        if (delta_max >= 0.3 + delta_met) conclusion = "не годен";

        Tag delta_pk_iTag = manualTagService.getTagByNameAndReportType("delta_pk_i", ManualReportTypesEnum.kmhDensityMeter.name());
        Tag rho_lpr_iTag = manualTagService.getTagByNameAndReportType("rho_lpr_i", ManualReportTypesEnum.kmhDensityMeter.name());
        Tag beta15Tag = manualTagService.getTagByNameAndReportType("beta15", ManualReportTypesEnum.kmhDensityMeter.name());
        Tag gamma_pl_iTag = manualTagService.getTagByNameAndReportType("gamma_pl_i", ManualReportTypesEnum.kmhDensityMeter.name());
        Tag conclusionTag = manualTagService.getTagByNameAndReportType("conclusion", ManualReportTypesEnum.kmhDensityMeter.name());

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(delta_pk_i),
                delta_pk_iTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(rho_lpr_i),
                rho_lpr_iTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(beta15),
                beta15Tag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                ArrayParser.fromObjectToJson(gamma_pl_i),
                gamma_pl_iTag,
                report
        ));

        reportDataList.add(new ReportData(
                null,
                conclusion,
                conclusionTag,
                report
        ));

        return reportDataList;
    }

    @Override
    protected Report createReport(LocalDateTime currentDt) {
        ReportType reportType = manualReportTypeService.findById(ManualReportTypesEnum.kmhDensityMeter.name());

        // Пока создается отчет без названия. Название будет установлено в методе generateReportDataList,
        // т.к. для названия необходимо значение тега workingOrReserve
        return new Report(
                null,
                "no name",
                currentDt,
                reportType
        );
    }

    private static double[] calculateRho_l_pr_and_beta_and_gamma(double P_pl, double t_pl, double rho15_1, double rho15_2, double delta_syst) {
        double rho15 = (rho15_1 + rho15_2) / 2 - delta_syst;
        double gamma = 0.001 * Math.exp(-1.6208 + 0.00021592 * t_pl + (870960 / Math.pow(rho15, 2)) + (4.2092 * t_pl * 1000 / Math.pow(rho15, 2)));
        double beta = 613.9723 / Math.pow(rho15, 2);
        double rho_lpr = rho15 * Math.exp(-beta * (t_pl - 15) * (1 + 0.8 * beta * (t_pl - 15))) / (1 - gamma * P_pl);
        return new double[]{rho_lpr, beta, gamma};
    }

    private static double calculateRho15(double rho_meas, double t_ar) {
        double rho0 = rho_meas * (1 - 0.000025 * (t_ar - 20));
        double betaPrev = 613.9723 / Math.pow(rho0, 2);
        double rho15Prev = rho0 * Math.exp(betaPrev * (t_ar - 15) * (1 + 0.8 * betaPrev * (t_ar - 15)));
        double beta = 613.9723 / Math.pow(rho15Prev, 2);
        double rho15 = rho0 * Math.exp(beta * (t_ar - 15) * (1 + 0.8 * beta * (t_ar - 15)));
        int cnt = 0;

        while (Math.abs(rho15 - rho15Prev) > 0.0001) {
            rho15Prev = rho15;
            beta = 613.9723 / Math.pow(rho15Prev, 2);
            rho15 = rho0 * Math.exp(beta * (t_ar - 15) * (1 + 0.8 * beta * (t_ar - 15)));
            if(cnt > 10000) {
                throw new RuntimeException("Не удается посчитать rho15 с помощью метода последовательных приближений, количество циклов вычислений превысило 10000");
            }
            cnt++;
        }
        return rho15;
    }
}
