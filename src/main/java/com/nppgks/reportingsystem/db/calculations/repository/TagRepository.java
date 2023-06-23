package com.nppgks.reportingsystem.db.calculations.repository;

import com.nppgks.reportingsystem.db.calculations.entity.Tag;
import com.nppgks.reportingsystem.dto.calc.CalcTagForOpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TagCalcRepository")
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Modifying
    @Query("""
            update calc_tag t
            set t.address = :address,
            t.description = :description where t.id = :id
            """)
    int updateTag(@Param("id") Integer id, @Param("address") String address, @Param("description") String description);

    List<CalcTagForOpc> findAllByInitialAndCalcMethod(Boolean initial, String type);

    List<Tag> findByOrderByPermanentName();
}
