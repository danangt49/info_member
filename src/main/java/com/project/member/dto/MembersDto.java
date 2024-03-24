package com.project.member.dto;

import com.project.member.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link MembersDto}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembersDto implements Serializable {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private String gender;
    private String identityNumber;
    private String photoUrl;
    private String address;
    private List<Roles> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}