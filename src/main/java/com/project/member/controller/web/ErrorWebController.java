package com.project.member.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ErrorWebController {

    @GetMapping("403")
    public String accessDenied() {
        return "error/403";
    }

    @GetMapping("/500")
    public String internalServerError() {
        return "error/500";
    }

    @GetMapping("/404")
    public String notFFound() {
        return "error/404";
    }
}
