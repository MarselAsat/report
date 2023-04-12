package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.ReportTypeDto;
import com.nppgks.reportingsystem.service.dbservices.ReportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportTypes")
@RequiredArgsConstructor
public class ReportTypeApiController {

    private final ReportTypeService reportTypeService;

    @GetMapping
    public List<ReportTypeDto> getAllReportTypes() {
        return reportTypeService.getAllReportTypes();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateReportType(@RequestBody Map<String, String> updates, @PathVariable String id){
        reportTypeService.partialUpdateReportType(id, updates);
        return ResponseEntity.ok("successfully updated");
    }
}
