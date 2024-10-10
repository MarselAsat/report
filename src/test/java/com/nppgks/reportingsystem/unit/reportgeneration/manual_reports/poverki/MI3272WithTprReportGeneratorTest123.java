package com.nppgks.reportingsystem.unit.reportgeneration.manual_reports.poverki;
import com.nppgks.reportingsystem.db.manual_reports.entity.ReportData;
import com.nppgks.reportingsystem.opcservice.OpcServiceRequests;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.SaveReportStrategy;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.MI3272WithTprReportGenerator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.MI3272Calculator;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.MI3272FinalData;
import com.nppgks.reportingsystem.reportgeneration.manual_reports.poverki.mi3272.calculations.MI3272InitData;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MI3272WithTprReportGeneratorTest123 {

    // Создаем моки для зависимостей
    private OpcServiceRequests opcServiceRequests = Mockito.mock(OpcServiceRequests.class);
    private ManualTagService manualTagService = Mockito.mock(ManualTagService.class);
    private ManualReportTypeService manualReportTypeService = Mockito.mock(ManualReportTypeService.class);
    private SaveReportStrategy saveReportStrategy = Mockito.mock(SaveReportStrategy.class);
    private MI3272InitData initData = Mockito.mock(MI3272InitData.class);

    @Test
    void testMI3272WithTprReportGenerator() {

        // Таблица 1
        Mockito.when(initData.getDelta_KP()).thenReturn(0.05);
        Mockito.when(initData.getD()).thenReturn(444.5);
        Mockito.when(initData.getS()).thenReturn(31.75);
        Mockito.when(initData.getE()).thenReturn(196500.0);
        Mockito.when(initData.getDelta_t_KP()).thenReturn(0.2);
        Mockito.when(initData.getDelta_PP()).thenReturn(0.03);
        Mockito.when(initData.getDelta_t_PP()).thenReturn(0.2);
        Mockito.when(initData.getDelta_UOI_K()).thenReturn(0.001);
        Mockito.when(initData.getKF_conf()).thenReturn(65454.5);
        Mockito.when(initData.getZS()).thenReturn(0.0682);
        Mockito.when(initData.getV_KP_0()).thenReturn(0.119454);
        Mockito.when(initData.getMF_set_range()).thenReturn(1.0);


        // Таблица 2
        Mockito.when(initData.getN_mas_ij()).thenReturn(new double[][] {
                         {1179666, 1180144, 1181765, 1179950, 1179109},
                         {1180095, 1179330, 1177888, 1180360, 1180693},
                         {1182529, 1181498, 1179606, 1180415, 1180545},
                         {1180653, 1181751, 1182398, 1179515, 1181165},
                         {1177599, 1178164, 1179274, 1179927, 1178474},
         });

                Mockito.when(initData.getT_KP_ij_avg()).thenReturn(new double[][]{
                {13.20848, 13.20463, 13.203671, 13.198812, 13.197015},
                {13.18631, 13.35469, 13.531353, 13.632994, 13.65826},
                {13.093396, 13.098581, 13.10614, 13.106156, 13.109241},
                {13.244493, 13.35044, 13.502419, 13.537302, 13.563994},
                {13.749858, 13.800687, 13.818151, 13.830619, 13.837958}
        });
                Mockito.when(initData.getP_KP_ij_avg()).thenReturn (new double[][] {
                {1.437914, 1.437763, 1.438334, 1.438301, 1.438719},
                {1.268818, 1.26886, 1.269291, 1.268851, 1.269054},
                {1.637646, 1.637226, 1.637582, 1.637785, 1.638554},
                {2.459271, 2.459309, 2.459214, 2.458488, 2.488933},
                {2.328182, 2.32828, 2.32853, 2.32847, 2.329509}
        });
        Mockito.when(initData.getRho_PP_ij_avg()).thenReturn (new double[][]   {
                {821.176941, 821.209229, 821.206665, 821.23053, 821.166626},
                {820.770813, 820.731018, 820.730652, 820.759277, 820.740967},
                {821.386963, 821.378601, 821.384521, 821.381714, 821.370239},
                {821.613708, 821.623169, 821.631958, 821.616089, 821.622253},
                {821.235413, 821.241638, 821.228149, 821.220398, 821.230652}
        });
        Mockito.when(initData.getT_PP_ij_avg()).thenReturn(new double[][] {
                {13.875558, 13.872135, 13.896128, 13.851083, 13.838077},
                {14.353182, 14.379037, 14.38749, 14.390039, 14.399739},
                {13.724781, 13.725098, 13.72224, 13.726476, 13.727546},
                {14.171124, 14.184172, 14.187254, 14.190256, 14.190944},
                {14.450851, 14.455412, 14.469972, 14.477814, 14.48641}
        });
        Mockito.when(initData.getP_PP_ij_avg()).thenReturn(new double[][]  {
                {1.556652, 1.557405, 1.576316, 1.578859, 1.582344},
                {1.430445, 1.431037, 1.432967, 1.431682, 1.431177},
                {1.830999, 1.830945, 1.832921, 1.831202, 1.832791},
                {2.715914, 2.717683, 2.718018, 2.716509, 2.720436},
                {2.611435, 2.608938, 2.613119, 2.610436, 2.611578}
        });

//        Mockito.when(initData.getW_w_ij()).thenReturn(new double[][] {
//                {4.20900000, 4.18631000, 4.09339600, 4.24449300, 4.74985800},
//                {4.20500000, 4.35469000, 4.09858100, 4.35044000, 4.80068700},
//                {4.20400000, 4.53453000, 4.10900000, 4.50267000, 4.81815100},
//                {4.19881200, 4.63299400, 4.10615600, 4.53730200, 4.83061900},
//                {4.19701500, 4.65826000, 4.10924100, 4.56399400, 4.83795800}
//
//        });
//
//        Mockito.when(initData.getW_xc_ij()).thenReturn(new double[][] {
//                {4.20900000, 4.18631000, 4.09339600, 4.24449300, 4.74985800},
//                {4.20500000, 4.35469000, 4.09858100, 4.35044000, 4.80068700},
//                {4.20400000, 4.53453000, 4.10900000, 4.50267000, 4.81815100},
//                {4.19881200, 4.63299400, 4.10615600, 4.53730200, 4.83061900},
//                {4.19701500, 4.65826000, 4.10924100, 4.56399400, 4.83795800}
//        });
        // Таблица 3
        Mockito.when(initData.getAlpha_cyl_t()).thenReturn(0.0000108);
        Mockito.when(initData.getAlpha_cyl_t_sq()).thenReturn(0.0000216);
        Mockito.when(initData.getAlpha_st_t()).thenReturn(0.00000144);


        // Группа, к которой принадлежит рабочая жидкость.
         Mockito.when(initData.getWorkingFluid()).thenReturn("нефть");
//
//        // Реализация градуировочных характеристик:
        Mockito.when(initData.getCalibrCharImpl()).thenReturn("ПЭП");
//
//        // применяют поточный ПП, установленный в БИК или на компакт-прувере
   //     Mockito.when(initData.getPPInKP()).thenReturn(true);
//
        Mockito.when(initData.getT_ij()).thenReturn(new double[][] {
                {1.52848400, 1.06089100, 0.82105600, 0.67157300, 0.55028400},
                {1.52071900, 1.06007200, 0.82103600, 0.67354900, 0.55000200},
                {1.55128000, 1.06025900, 0.82129500, 0.67252400, 0.55638100},
                {1.52228300, 1.06058600, 0.82044200, 0.67396800, 0.55243700},
                {1.55218900, 1.06065100, 0.82185600, 0.67494400, 0.55249800}
        });
        Mockito.when(initData.getT_st_ij()).thenReturn(new double[][] {
                {14.79673000, 15.23979900, 14.34462000, 14.18185400, 13.99197000},
                {14.79673000, 15.23979900, 14.34462000, 14.18185400, 13.99197000},
                {14.79673000, 15.23979900, 14.34462000, 14.18185400, 13.99197000},
                {14.79673000, 15.23979900, 14.34462000, 14.18185400, 13.99197000},
                {14.79673000, 15.23979900, 14.34462000, 14.18185400, 13.99197000}
        });
//
        Mockito.when(initData.getK_PEP_gr()).thenReturn(65454.5);
       // Mockito.when(initData.getPPInKP()).thenReturn(false);


//
//        // Эти параметры не используются в вычислениях. Только отображаются на html странице протокола поверки
//        Mockito.when(initData.getProtocolNumber()).thenReturn(protocolNumber);
//        Mockito.when(initData.getMassmeterModel()).thenReturn(massmeterModel);
//        Mockito.when(initData.getPlace_name()).thenReturn(place_name);
//        Mockito.when(initData.getPlace_owner()).thenReturn(place_owner);
//        Mockito.when(initData.getMassmeterSensor()).thenReturn(massmeterSensor);
//        Mockito.when(initData.getMassmeterDu()).thenReturn(massmeterDu);
//        Mockito.when(initData.getMassmeterNumber()).thenReturn(massmeterNumber);
//        Mockito.when(initData.getPepModel()).thenReturn(pepModel);
//        Mockito.when(initData.getPepNumber()).thenReturn(pepNumber);
//        Mockito.when(initData.getInstalledOn()).thenReturn(installedOn);
//        Mockito.when(initData.getIlNumber()).thenReturn(ilNumber);
//        Mockito.when(initData.getCpType()).thenReturn(cpType);
//        Mockito.when(initData.getCpGrade()).thenReturn(cpGrade);
//        Mockito.when(initData.getCpNumber()).thenReturn(cpNumber);
//        Mockito.when(initData.getCpDate()).thenReturn(cpDate);
//        Mockito.when(initData.getTprType()).thenReturn(tprType);
//        Mockito.when(initData.getTprRange()).thenReturn(tprRange);
//        Mockito.when(initData.getTprNumber()).thenReturn(tprNumber);
//        Mockito.when(initData.getPpType()).thenReturn(ppType);
//        Mockito.when(initData.getPpNumber()).thenReturn(ppNumber);
//        Mockito.when(initData.getPpDate()).thenReturn(ppDate);
//        Mockito.when(initData.getCompanyName()).thenReturn(companyName);
//        Mockito.when(initData.getVerifierName()).thenReturn(verifierName);

        MI3272Calculator mi3272Calculator = new MI3272Calculator();
        mi3272Calculator.initData(initData);
        mi3272Calculator.calculate();
//
//
//        // Создаем экземпляр класса MI3272WithTprReportGenerator с моками зависимостей
//        MI3272WithTprReportGenerator generator = new MI3272WithTprReportGenerator(saveReportStrategy, opcServiceRequests, manualTagService, manualReportTypeService);
//
//        // Создаем тестовые данные для tprCoeffInitDataFromOpc
//        Map<String, String> tprCoeffInitDataFromOpc = new HashMap<>();
//        tprCoeffInitDataFromOpc.put("T_ij", "231,123");
//        tprCoeffInitDataFromOpc.put("t_st_ij", "1179666");
//        tprCoeffInitDataFromOpc.put("K_PEP_gr", "13");
//        // ... добавляем другие данные, если нужно
//
//        // Устанавливаем тестовые данные для tprCoeffInitDataFromOpc
//        generator.setTprCoeffInitDataFromOpc(tprCoeffInitDataFromOpc);
//
//        // Генерируем коэффициент TPR
//        String tagValueK_j = generator.generateTprCoeff();
//
//        // Проверяем результат генерации коэффициента TPR
//        assertNotNull(tagValueK_j);
//        // ... добавляем дополнительные проверки, если нужно
//
//        // Генерируем данные для отчета
//        List<ReportData> reportDataList = generator.generateReportDataList();
//
//        // Проверяем результат генерации данных отчета
//        assertNotNull(reportDataList);
//        assertFalse(reportDataList.isEmpty());
//        // ... добавляем дополнительные проверки, если нужно
    }
}