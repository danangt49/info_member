package com.project.member.vo;

import com.project.member.validator.CheckEmailUserConstrains;
import com.project.member.validator.CheckNameConstrains;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link com.project.member.entity.Users}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersAddVo implements Serializable {
    private String id;

    @NotNull
    @NotEmpty
    @CheckNameConstrains
    private String name;

    @Email(message = "Invalid email")
    @NotNull
    @NotEmpty
    @CheckEmailUserConstrains
    private String email;

    @NotNull
    @NotEmpty
    @Length(min = 6, message = "Password must be at least 6 characters")
    private String password;
}