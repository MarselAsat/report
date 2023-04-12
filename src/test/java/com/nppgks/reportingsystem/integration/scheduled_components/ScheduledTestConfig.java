package com.nppgks.reportingsystem.integration.scheduled_components;

import com.nppgks.reportingsystem.constants.ReportTypesEnum;
import com.nppgks.reportingsystem.constants.SettingsConstants;
import com.nppgks.reportingsystem.db.operative_reports.entity.ReportType;
import com.nppgks.reportingsystem.service.dbservices.ReportTypeService;
import com.nppgks.reportingsystem.service.dbservices.SettingsService;
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
