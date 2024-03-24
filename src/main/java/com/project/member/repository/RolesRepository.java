package com.project.member.repository;

import com.project.member.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, String> {
    List<Roles> findByName(String name);
}