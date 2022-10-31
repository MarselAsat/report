package com.nppgks.reports.integration.scheduled_components;

import com.nppgks.reports.constants.ReportTypesEnum;
import com.nppgks.reports.constants.SettingsConstants;
import com.nppgks.reports.db.recurring_reports.entity.ReportType;
import com.nppgks.reports.service.db_services.ReportTypeService;
import com.nppgks.reports.service.db_services.SettingsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.annotation.PostConstruct;

import java.util.LinkedHashMap;

import static org.mockito.Mockito.doReturn;

@TestConfiguration
public class ScheduledTestConfig {

    @SpyBean
    private SettingsService settingsService;
    @SpyBean
    private ReportTypeService reportTypeService;

    @PostConstruct
    public void initMock(){
        doReturn("10:00").when(settingsService)
                .getStringValueBySettingName(SettingsConstants.START_DAILY_REPORT);

        doReturn("12:00").when(settingsService)
                .getStringValueBySettingName(SettingsConstants.START_MONTH_REPORT);

        doReturn("11:00").when(settingsService)
                .getStringValueBySettingName(SettingsConstants.START_YEAR_REPORT);

        LinkedHashMap<String, String> shiftNumAndStartTime = new LinkedHashMap<>();
        shiftNumAndStartTime.put("1", "10:00");
        shiftNumAndStartTime.put("2", "22:00");
        doReturn(shiftNumAndStartTime).when(settingsService)
                .getMapValuesBySettingName(SettingsConstants.START_SHIFT_REPORT);

        ReportType shiftReportTYpe = new ReportType();
        shiftReportTYpe.setId("shift");
        shiftReportTYpe.setActive(true);
        shiftReportTYpe.setName("Сменный отчет");
        doReturn(shiftReportTYpe).when(reportTypeService)
                .getReportTypeById(ReportTypesEnum.shift.name());


    }
}
