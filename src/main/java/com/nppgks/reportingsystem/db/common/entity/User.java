package com.nppgks.reportingsystem.db.common.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "\"user\"", schema = "public")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
}
