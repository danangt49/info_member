package com.project.member.service;

import com.project.member.dto.UsersDto;
import com.project.member.vo.UsersAddVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UsersService {
    Map<Object, String> save(UsersAddVo vo);
    UsersDto getById(String id);
    Page<UsersDto> list(Pageable pageable);
    Map<Object, String> delete(String id);
}
