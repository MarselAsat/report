package com.nppgks.reportingsystem.controller.rest;

import com.nppgks.reportingsystem.dto.TagDto;
import com.nppgks.reportingsystem.service.dbservices.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagApiController {
    private final TagService tagService;

    @PostMapping
    public Long createTagName(@RequestBody TagDto tagDto){
        return tagService.saveTag(tagDto);
    }

    @GetMapping
    public List<TagDto> getAllTags(){
        return tagService.getAllTagsDto();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return ResponseEntity.ok("successfully deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partUpdateTag(@RequestBody Map<String, String> updates, @PathVariable Long id){
        tagService.partialUpdateTag(id, updates);
        return ResponseEntity.ok("successfully updated");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@RequestBody TagDto tagDto){
        tagService.saveTag(tagDto);
        return ResponseEntity.ok("successfully updated");
    }
}
