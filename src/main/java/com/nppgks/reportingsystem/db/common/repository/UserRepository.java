package com.nppgks.reportingsystem.db.common.repository;

import com.nppgks.reportingsystem.db.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUsername(String username);
}
