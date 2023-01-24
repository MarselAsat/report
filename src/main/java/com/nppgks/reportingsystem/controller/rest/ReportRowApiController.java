package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.ReportRowDto;
import com.nppgks.reportingsystem.service.dbservices.ReportRowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportRows")
@RequiredArgsConstructor
public class ReportRowApiController {

    private final ReportRowService reportRowService;

    @GetMapping
    public List<ReportRowDto> getReportRows() {
        return reportRowService.getAllReportRows();
    }
    @PostMapping
    public Integer createReportRow(@RequestBody ReportRowDto reportRowDto){
        return reportRowService.saveReportRow(reportRowDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReportRow(@PathVariable Integer id){
        reportRowService.deleteReportRow(id);
        return ResponseEntity.ok("successfully deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateReportRow(@RequestBody Map<String, String> updates, @PathVariable Integer id){
        reportRowService.partialUpdateReportRow(id, updates);
        return ResponseEntity.ok("successfully updated");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReportRow(@RequestBody ReportRowDto reportRowDto){
        reportRowService.saveReportRow(reportRowDto);
        return ResponseEntity.ok("successfully updated");
    }
}
