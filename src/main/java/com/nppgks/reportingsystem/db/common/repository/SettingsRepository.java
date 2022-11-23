package com.nppgks.reportingsystem.db.common.repository;

import com.nppgks.reportingsystem.db.common.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SettingsRepository extends JpaRepository<Settings, Integer> {

    Optional<Settings> findByName(String name);

    @Modifying
    @Query("update Settings u " +
            "set u.value = :value " +
            "where u.name = :name")
    void update(@Param("name") String name, @Param("value") String value);
}
