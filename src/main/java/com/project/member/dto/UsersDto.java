package com.project.member.dto;

import com.project.member.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link UsersDto}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto implements Serializable {
    private String id;
    private String name;
    private String email;
    private List<Roles> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}