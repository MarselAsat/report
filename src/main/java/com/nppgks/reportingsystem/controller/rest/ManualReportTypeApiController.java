package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.db.manual_reports.entity.ReportType;
import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualReportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manualReportTypes")
@RequiredArgsConstructor
public class ManualReportTypeApiController {

    private final ManualReportTypeService manualReportTypeService;

    @GetMapping
    public List<ReportType> getAllReportTypes() {
        return manualReportTypeService.getAllReportTypes();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateReportType(@RequestBody Map<String, String> updates, @PathVariable String id){
        manualReportTypeService.partialUpdateReportType(id, updates);
        return ResponseEntity.ok("successfully updated");
    }
}
