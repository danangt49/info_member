package com.project.member.controller.web;

import com.google.common.base.Strings;
import com.project.member.dto.UsersDto;
import com.project.member.service.UsersService;
import com.project.member.vo.UsersAddVo;
import com.project.member.util.paging.Paged;
import com.project.member.util.paging.Paging;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
@Slf4j
@RequiredArgsConstructor
public class UserWebController {
    private final UsersService usersService;

    @GetMapping
    public String list(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            Model model
    ) {
        PageRequest pageable =
                PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<UsersDto> places = usersService.list(pageable);
        Paged<UsersDto> paged =
                new Paged<>(
                        places, Paging.of(places.getTotalPages(), pageNumber, size, places.getTotalElements()));

        model.addAttribute("users", paged);
        model.addAttribute(
                "info",
                "Showing "
                        + (places.getContent().size())
                        + "  of "
                        + paged.getContents().getTotalElements()
                        + " entries");

        return "user/list";
    }

    @GetMapping("{id}")
    public String viewDetail(Model model, @PathVariable String id) {
        model.addAttribute("user", usersService.getById(id));

        return "user/view";
    }

    @GetMapping(value = {"form"})
    public String createForm(
            Model model,
            @RequestParam(value = "id", required = false, defaultValue = "") String id
    ) {
        if (!id.isEmpty()) {
            model.addAttribute("vo", usersService.getById(id));
        }else {
            passObjectModelCreateForm(model, new UsersAddVo());
        }

        return "user/form";
    }

    @PostMapping(value = {"form"})
    public String save(Model model, @Valid @ModelAttribute("vo") UsersAddVo vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (Strings.isNullOrEmpty(vo.getId())) {
                model.addAttribute("vo", vo);
                return "user/form";
            }
        }

        usersService.save(vo);
        return "redirect:/users";
    }

    @PostMapping("delete/{id}")
    public String delete(Model model, @PathVariable String id) {
        try {
            usersService.delete(id);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    private void passObjectModelCreateForm(Model model, UsersAddVo vo) {
        model.addAttribute("vo", vo);
    }
}
