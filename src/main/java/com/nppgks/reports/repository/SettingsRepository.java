package com.nppgks.reports.repository;

import com.nppgks.reports.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Integer> {

    Settings findByName(String name);
}
