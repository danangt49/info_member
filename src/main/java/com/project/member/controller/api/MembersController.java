package com.project.member.controller.api;

import com.project.member.config.GlobalApiResponse;
import com.project.member.service.MembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/members", produces = MediaType.APPLICATION_JSON_VALUE)
public class MembersController {

    private final MembersService membersService;

    @GetMapping
    public GlobalApiResponse<?> page(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
        return new GlobalApiResponse<>(HttpStatus.OK.value(), membersService.getListByName(name, pageable));
    }
}
