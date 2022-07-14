package com.nppgks.reports.scheduledComponents;

import com.nppgks.reports.dto.TagNameDto;
import com.nppgks.reports.dto.TagNameMapper;
import com.nppgks.reports.entity.ReportName;
import com.nppgks.reports.entity.ReportType;
import com.nppgks.reports.entity.TagData;
import com.nppgks.reports.entity.TagName;
import com.nppgks.reports.service.ReportNameService;
import com.nppgks.reports.service.ReportTypeService;
import com.nppgks.reports.service.TagDataService;
import com.nppgks.reports.service.TagNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class ScheduledTasks {
    Random random = new Random();

    private TagNameService<TagNameDto, Long> tagNameService;

    private ReportNameService reportNameService;

    private ReportTypeService reportTypeService;

    private TagDataService tagDataService;

    @Autowired
    public ScheduledTasks(TagNameService<TagNameDto, Long> tagNameService, ReportNameService reportNameService, ReportTypeService reportTypeService, TagDataService tagDataService) {
        this.tagNameService = tagNameService;
        this.reportNameService = reportNameService;
        this.reportTypeService = reportTypeService;
        this.tagDataService = tagDataService;
    }

    public ScheduledTasks(){

    }

//    @Scheduled(cron = "0 0 0/1 * * ?") // every hour
//    @Scheduled(cron = "0 0/1 * * * ?") // every minute
    public void createTagDataEveryHour() {
        ReportType hourReportType = reportTypeService.getReportTypeById(1).get();
        List<TagName> tagNames = tagNameService.getAllTagNames()
                .stream()
                .map(tagName -> new TagNameMapper(reportTypeService).toTagName(tagName))
                .toList();
        String name = "Часовой отчет за "+ LocalTime.now().getHour()+" часов";
        ReportName reportName = new ReportName(null, name, LocalDateTime.now(), hourReportType);
        reportNameService.saveReportName(reportName);

        List<String> tagNamesStr = tagNames.stream()
                .map(tagName ->
                    tagName.getName())
                .toList();
        Map<String, String> tagDataFromOPC = getTagDataFromOPC(tagNamesStr);
        tagDataService.saveTagDataMapByReportName(tagDataFromOPC, reportName, LocalDateTime.now());
    }

    private Map<String, String> getTagDataFromOPC(List<String> tagNames){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://192.168.55.131:8080/opc";
        List<String> requestJson = tagNames;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<String>> entity = new HttpEntity<>(requestJson,headers);
        Map<String, String> map = restTemplate.postForObject(url, entity, HashMap.class);
        return map;
    }
}
