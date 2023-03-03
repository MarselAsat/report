package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.recurring_reports.entity.MeteringNode;
import com.nppgks.reportingsystem.db.recurring_reports.repository.MeteringNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeteringNodeService {

    private final MeteringNodeRepository meteringNodeRepository;

    public List<MeteringNode> getAllNodes(){
        return meteringNodeRepository.findAll();
    }
}
