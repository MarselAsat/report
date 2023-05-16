package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.operative_reports.repository.TagNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllTagNamesService {
    private final TagNameRepository operativeRepository;
    private final com.nppgks.reportingsystem.db.calculations.repository.TagNameRepository calculationRepository;

    public List<String> getAllOperativeAndCalculationTagNames() {
        List<String> allTagNames = new ArrayList<>();
        allTagNames.addAll(operativeRepository.findAll().stream()
                .map(tn -> tn.getName()).toList());
        allTagNames.addAll(calculationRepository.findAll().stream()
                .map(tn -> tn.getName()).toList());

        return allTagNames;
    }
}
