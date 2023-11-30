package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.manual.ManualTagDto;
import com.nppgks.reportingsystem.service.dbservices.manual_reports.ManualTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manualTags")
@RequiredArgsConstructor
public class ManualTagApiController {
    private final ManualTagService tagService;

    @GetMapping
    public List<ManualTagDto> getAllTags(){
        return tagService.getAllTags();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateTag(@RequestBody Map<String, String> updates, @PathVariable Integer id){
        tagService.partialUpdateTag(id, updates);
        return ResponseEntity.ok("successfully updated");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@RequestBody ManualTagDto tagNameDto){
        tagService.saveTag(tagNameDto);
        return ResponseEntity.ok("successfully updated");
    }
}

