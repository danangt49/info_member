package com.project.member.validator.impl;

import com.project.member.repository.MembersRepository;
import com.project.member.repository.UsersRepository;
import com.project.member.validator.CheckNameConstrains;
import com.project.member.validator.CheckNameMemberConstrains;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckNameMemberValidatorImpl implements ConstraintValidator<CheckNameMemberConstrains, String>  {
    private final MembersRepository membersRepository;

    @Override
    public void initialize(CheckNameMemberConstrains constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        var user = membersRepository.findByName(s);
        return user.isPresent();
    }
}
