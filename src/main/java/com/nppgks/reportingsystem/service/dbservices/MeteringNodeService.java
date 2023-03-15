package com.nppgks.reportingsystem.service.dbservices;

import com.nppgks.reportingsystem.db.operative_reports.entity.MeteringNode;
import com.nppgks.reportingsystem.db.operative_reports.repository.MeteringNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeteringNodeService {

    private final MeteringNodeRepository meteringNodeRepository;

    public List<MeteringNode> getAllNodes(){
        return meteringNodeRepository.findAllByOrderById();
    }

    public void partialUpdateMeteringNode(String id, Map<String, String> updates) {
        Optional<MeteringNode> meteringNodeOpt = meteringNodeRepository.findById(id);
        if(meteringNodeOpt.isPresent()){
            MeteringNode updatedMeteringNode = PartialUpdateService.updateObject(meteringNodeOpt.get(), updates);
            meteringNodeRepository.save(updatedMeteringNode);
        }
        else {
            throw new NoSuchElementException();
        }
    }

    public String saveMeteringNode(MeteringNode meteringNode) {
        return meteringNodeRepository.save(meteringNode).getId();
    }
}
