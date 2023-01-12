package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.TagNameDto;
import com.nppgks.reportingsystem.service.dbservices.TagNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tagNames")
@RequiredArgsConstructor
public class TagNameApiController {
    private final TagNameService tagNameService;

    @PostMapping
    public Long createTagName(@RequestBody TagNameDto tagNameDto){
        return tagNameService.saveTagName(tagNameDto);
    }

    @GetMapping
    public List<TagNameDto> getAllTagNames(){
        return tagNameService.getAllTagNamesDto();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTagName(@PathVariable Long id){
        tagNameService.deleteTagName(id);
        return ResponseEntity.ok("successfully deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateTagName(@RequestBody Map<String, String> updates, @PathVariable Long id){
        tagNameService.partialUpdateTagName(id, updates);
        return ResponseEntity.ok("successfully updated");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTagName(@RequestBody TagNameDto tagNameDto){
        tagNameService.saveTagName(tagNameDto);
        return ResponseEntity.ok("successfully updated");
    }
}
