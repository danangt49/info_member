package com.project.member.service;

import com.project.member.vo.RegisterVo;

import java.util.Map;

public interface AuthService {
    Map<Object, String> register(RegisterVo vo);
}
