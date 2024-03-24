package com.project.member.validator.impl;

import com.project.member.repository.UsersRepository;
import com.project.member.validator.CheckNameConstrains;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckNameValidatorImpl implements ConstraintValidator<CheckNameConstrains, String>  {
    private final UsersRepository usersRepository;

    @Override
    public void initialize(CheckNameConstrains constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        var user = usersRepository.findByName(s);
        return user.isPresent();
    }
}
