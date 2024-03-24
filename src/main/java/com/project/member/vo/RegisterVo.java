package com.project.member.vo;

import com.project.member.entity.enumeric.Gender;
import com.project.member.validator.CheckEmailConstraints;
import com.project.member.validator.CheckNameMemberConstrains;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVo {
    @Length(min = 3, max = 50)
    @NotNull
    @NotEmpty
    @CheckNameMemberConstrains
    private String name;

    @Email(message = "Invalid email")
    @NotNull
    @NotEmpty
    @CheckEmailConstraints
    private String email;

    @Length(min = 6, message = "Password must be at least 6 characters")
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String phoneNumber;

    @NotNull()
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotNull
    private Gender gender;

    @NotNull
    @NotEmpty
    private String identityNumber;

    @NotNull
    private MultipartFile photoUrl;

    private String address;
}
