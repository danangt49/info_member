package com.project.member.validator.impl;

import com.project.member.repository.UsersRepository;
import com.project.member.validator.CheckEmailUserConstrains;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckEmailUserValidatorImpl implements ConstraintValidator<CheckEmailUserConstrains, String> {
    private final UsersRepository usersRepository;
    @Override
    public void initialize(CheckEmailUserConstrains constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        var user = usersRepository.findByEmail(s);
        return user.isEmpty();
    }
}
