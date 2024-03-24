package com.project.member.validator;

import com.project.member.validator.impl.CheckNameValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.project.member.util.MyConstants.NAME_ALREADY;


@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckNameValidatorImpl.class)
public @interface CheckNameConstrains {
    String message() default NAME_ALREADY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
