package com.project.member.service;

import com.project.member.dto.MembersDto;
import com.project.member.vo.MembersAddVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface MembersService {
    Map<Object, String> save(MembersAddVo vo);
    MembersDto getById(String id);
    Page<MembersDto> list(Pageable pageable);
    Page<MembersDto> getListByName(String name, Pageable pageable);
    Map<Object, String> delete(String id);
}
