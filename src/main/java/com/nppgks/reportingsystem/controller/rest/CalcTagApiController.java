package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.calc.CalcTagDto;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calcTags")
@RequiredArgsConstructor
public class CalcTagApiController {
    private final CalcTagService tagService;

    @GetMapping
    public List<CalcTagDto> getAllTags(){
        return tagService.getAllTags();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateTag(@RequestBody Map<String, String> updates, @PathVariable Integer id){
        tagService.partialUpdateTag(id, updates);
        return ResponseEntity.ok("successfully updated");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@RequestBody CalcTagDto tagNameDto){
        tagService.saveTag(tagNameDto);
        return ResponseEntity.ok("successfully updated");
    }
}

