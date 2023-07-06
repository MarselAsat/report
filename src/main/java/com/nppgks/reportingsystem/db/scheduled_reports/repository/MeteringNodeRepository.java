package com.nppgks.reportingsystem.db.scheduled_reports.repository;

import com.nppgks.reportingsystem.db.scheduled_reports.entity.MeteringNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeteringNodeRepository extends JpaRepository<MeteringNode, String> {

    Optional<MeteringNode> findByName(String name);

    List<MeteringNode> findAllByOrderById();
}
