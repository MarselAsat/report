package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.MeteringNode;
import com.nppgks.reportingsystem.service.dbservices.MeteringNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/meteringNodes")
@RequiredArgsConstructor
public class MeteringNodeApiController {

    private final MeteringNodeService meteringNodeService;

    @GetMapping
    public List<MeteringNode> getAllMeteringNodes() {
        return meteringNodeService.getAllNodes();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateMeteringNode(@RequestBody Map<String, String> updates, @PathVariable String id) {
        meteringNodeService.partialUpdateMeteringNode(id, updates);
        return ResponseEntity.ok("successfully updated");
    }

    @PostMapping
    public String createMeteringNode(@RequestBody MeteringNode meteringNode) {
        return meteringNodeService.saveMeteringNode(meteringNode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeteringNode(@PathVariable String id) {
        meteringNodeService.deleteMeteringNode(id);
        return ResponseEntity.ok("successfully deleted");
    }
}
