package com.project.member.controller.web;

import com.project.member.dto.MembersDto;
import com.project.member.service.MembersService;
import com.project.member.vo.MembersAddVo;
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
@RequestMapping("members")
@Slf4j
@RequiredArgsConstructor
public class MembersWebController {
    private final MembersService membersService;

    @GetMapping
    public String list(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            Model model
    ) {
        PageRequest pageable =
                PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<MembersDto> member = membersService.list(pageable);
        Paged<MembersDto> paged = new Paged<>(member, Paging.of(member.getTotalPages(), pageNumber, size,
                member.getTotalElements()));

        model.addAttribute("members", paged);
        model.addAttribute("info", "Showing " + (member.getContent().size()) + "  of "
                + paged.getContents().getTotalElements() + " entries");
        return "member/list";
    }

    @GetMapping("{id}")
    public String viewDetail(Model model, @PathVariable String id) {
        model.addAttribute("member", membersService.getById(id));
        return "member/view";
    }

    @GetMapping(value = {"form"})
    public String createForm(
            Model model,
            @RequestParam(value = "id", required = false, defaultValue = "") String id
    ) {
        if (!id.isEmpty()) {
            model.addAttribute("vo", membersService.getById(id));
        }else {
            passObjectModelCreateForm(model, new MembersAddVo());
        }

        return "member/form";
    }

    @PostMapping("form")
    public String save(Model model, @Valid @ModelAttribute("vo") MembersAddVo vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vo", vo);
            return "member/form";
        }
        model.addAttribute("vo", membersService.save(vo));

        return "redirect:/members";
    }

    @PostMapping("delete/{id}")
    public String delete(Model model, @PathVariable String id) {
        try {
            membersService.delete(id);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/members";
    }

    private void passObjectModelCreateForm(Model model, MembersAddVo vo) {
        model.addAttribute("vo", vo);
    }
}
