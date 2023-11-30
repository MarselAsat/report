package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.scheduled_reports.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllTagsService {
    private final TagRepository scheduledRepository;
    private final com.nppgks.reportingsystem.db.manual_reports.repository.TagRepository calculationRepository;

    public List<String> getAllScheduledAndManualTags() {
        List<String> allTags = new ArrayList<>();
        allTags.addAll(scheduledRepository.findAll().stream()
                .map(t -> t.getAddress()).toList());
        allTags.addAll(calculationRepository.findAll().stream()
                .map(t -> t.getAddress()).toList());

        return allTags;
    }
}
