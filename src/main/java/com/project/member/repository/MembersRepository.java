package com.project.member.repository;

import com.project.member.entity.Members;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members, String> {
    @Query("SELECT m FROM Members AS m")
    Page<Members> getAll(Pageable pageable);
    Optional<Members> findByName(String name);
    Optional<Members> findByEmail(String email);
    @Query("SELECT m FROM Members AS m WHERE (?1 IS NULL OR m.name like %?1%)")
    Page<Members> getListByName(String name, Pageable pageable);

}