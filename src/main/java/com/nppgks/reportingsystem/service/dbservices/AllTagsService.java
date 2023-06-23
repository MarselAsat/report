package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.operative_reports.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllTagsService {
    private final TagRepository operativeRepository;
    private final com.nppgks.reportingsystem.db.calculations.repository.TagRepository calculationRepository;

    public List<String> getAllOperativeAndCalculationTags() {
        List<String> allTags = new ArrayList<>();
        allTags.addAll(operativeRepository.findAll().stream()
                .map(t -> t.getAddress()).toList());
        allTags.addAll(calculationRepository.findAll().stream()
                .map(t -> t.getAddress()).toList());

        return allTags;
    }
}
