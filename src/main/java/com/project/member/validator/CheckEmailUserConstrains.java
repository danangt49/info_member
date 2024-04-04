package com.project.member.validator;

import com.project.member.validator.impl.CheckEmailUserValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.project.member.util.MyConstants.EMAIL_ALREADY;
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckEmailUserValidatorImpl.class)
public @interface CheckEmailUserConstrains {
    String message() default EMAIL_ALREADY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
