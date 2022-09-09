package com.nppgks.reports.repository;

import com.nppgks.reports.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SettingsRepository extends JpaRepository<Settings, Integer> {

    Settings findByName(String name);

    @Modifying
    @Query("update Settings u " +
            "set u.value = :value " +
            "where u.name = :name")
    void update(@Param("name") String name, @Param("value") String value);
}
