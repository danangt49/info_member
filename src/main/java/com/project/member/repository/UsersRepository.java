package com.project.member.repository;

import com.project.member.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    @Query("SELECT u FROM Users AS u")
    Page<Users> getAll(Pageable pageable);

    @Query("SELECT u FROM Users AS u where u.name = ?1")
    Optional<Users> findByName(String name);
    @Query("SELECT u FROM Users AS u")
    Page<Users> getListByName(String role, String name, Pageable pageable);

    @Query("SELECT u FROM Users AS u where u.email = ?1")
    Optional<Users> findByEmail(String s);
}