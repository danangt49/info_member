package com.project.member.controller.web;

import com.project.member.config.exception.CustomException;
import com.project.member.entity.Members;
import com.project.member.entity.Users;
import com.project.member.service.AuthService;
import com.project.member.vo.RegisterVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthWebController {
    private final AuthService authService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @GetMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Users) {
            Users user = (Users) principal;
            model.addAttribute("profile", user);
        } else if (principal instanceof Members) {
            Members member = (Members) principal;
            model.addAttribute("profile", member);
        } else {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        }
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("vo", new RegisterVo());
        return "register";
    }

    @PostMapping("/register")
    public String saveRegister(Model model, @Valid @ModelAttribute("vo") RegisterVo vo, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vo", vo);
            return "register";
        }

        model.addAttribute("register", authService.register(vo));
        return "redirect:/login";
    }
}
