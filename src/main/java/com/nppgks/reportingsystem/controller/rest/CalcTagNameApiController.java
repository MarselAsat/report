package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.calc.CalcTagNameDto;
import com.nppgks.reportingsystem.service.dbservices.calculation.CalcTagNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calcTagNames")
@RequiredArgsConstructor
public class CalcTagNameApiController {
    private final CalcTagNameService tagNameService;

    @GetMapping
    public List<CalcTagNameDto> getAllTagNames(){
        return tagNameService.getAllTagNames();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateTagName(@RequestBody Map<String, String> updates, @PathVariable Integer id){
        tagNameService.partialUpdateTagName(id, updates);
        return ResponseEntity.ok("successfully updated");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTagName(@RequestBody CalcTagNameDto tagNameDto){
        tagNameService.saveTagName(tagNameDto);
        return ResponseEntity.ok("successfully updated");
    }
}

