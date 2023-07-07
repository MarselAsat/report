package com.nppgks.reportingsystem.db.manual_reports.repository;

import com.nppgks.reportingsystem.db.manual_reports.entity.Tag;
import com.nppgks.reportingsystem.dto.manual.ManualTagForOpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("TagManualRepository")
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Modifying
    @Query("""
            update manual_tag t
            set t.address = :address,
            t.description = :description where t.id = :id
            """)
    int updateTag(@Param("id") Integer id, @Param("address") String address, @Param("description") String description);

    List<ManualTagForOpc> findAllByInitialAndReportType(Boolean initial, String reportType);

    List<Tag> findByOrderByPermanentName();

    Optional<Tag> findByPermanentNameAndReportType(String name, String reportType);
}
