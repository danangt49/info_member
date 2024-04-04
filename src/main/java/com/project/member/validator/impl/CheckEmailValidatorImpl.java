package com.project.member.validator.impl;

import com.project.member.repository.MembersRepository;
import com.project.member.validator.CheckEmailConstraints;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckEmailValidatorImpl implements ConstraintValidator<CheckEmailConstraints, String> {
    private final MembersRepository membersRepository;

    @Override
    public void initialize(CheckEmailConstraints constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        var member = membersRepository.findByName(s);
        return member.isEmpty();
    }
}
