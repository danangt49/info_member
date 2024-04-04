package com.project.member.service.impl;

import com.project.member.config.exception.CustomException;
import com.project.member.dto.UsersDto;
import com.project.member.entity.Users;
import com.project.member.repository.RolesRepository;
import com.project.member.repository.UsersRepository;
import com.project.member.service.UsersService;
import com.project.member.vo.UsersAddVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.project.member.util.MyConstants.*;
import static com.project.member.util.UtilFn.createUsernameFromName;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    @Override
    public Map<Object, String> save(UsersAddVo vo) {
        Map<Object, String> ret = new HashMap<>();
        try {
            log.info("checking data user add or update");
            Optional<Users> existingUser = vo.getId() != null ? usersRepository.findById(vo.getId()) : Optional.empty();
            String name = vo.getName();
            String email = vo.getEmail();
            log.info("email {} :", email);

            log.info("checking data user by name");
            if (existingUser.isPresent() && !existingUser.get().getName().equals(name)) {
                Optional<Users> checkName = usersRepository.findByName(name);
                if (checkName.isPresent()) {
                    log.error("name already used");
                    throw new CustomException(NAME_ALREADY, HttpStatus.BAD_REQUEST);
                }

                Optional<Users> checkEmail = usersRepository.findByEmail(email);
                if (checkEmail.isPresent()) {
                    log.error("email already used");
                    throw new CustomException(EMAIL_ALREADY, HttpStatus.BAD_REQUEST);
                }
            }


            Users user = existingUser.orElse(new Users());
            user.setName(name);
            if (vo.getId() == null || existingUser.isEmpty()) {
                user.setUsername(createUsernameFromName(name));
                user.setPassword(new BCryptPasswordEncoder(12).encode(vo.getPassword()));
                user.setRoles(rolesRepository.findByName("USER"));
                user.setCreatedAt(LocalDateTime.now());
            }
            user.setEmail(vo.getEmail());
            user.setUpdatedAt(LocalDateTime.now());

            usersRepository.save(user);
            ret.put("status", SUCCESS);
            ret.put("message", "Success add or update user");

            log.info("user add or update successfully with name: {}", name);
        } catch (Exception e) {
            ret.put("status", ERROR);
            ret.put("message", "Failed to add or update user");

            log.error("Failed to add or update user", e);
        }
        return ret;
    }

    @Override
    public UsersDto getById(String id) {
        return toUserDto(findById(id));
    }

    @Override
    public Page<UsersDto> list(Pageable pageable) {
        return usersRepository.getAll(pageable).map(this::toUserDto);
    }

    @Override
    public Map<Object, String> delete(String id) {
        Map<Object, String> ret = new HashMap<>();
        try {
            var user = findById(id);
            user.setRoles(Collections.emptyList());
            usersRepository.delete(user);

            ret.put("status", SUCCESS);
            ret.put("message", "Success Delete user");

            log.info("user delete successfully with name: {}", user.getName());
        } catch (Exception e) {
            ret.put("status", ERROR);
            ret.put("message", "Failed to delete user");

            log.error("Failed to delete userr", e);
        }
        return ret;
    }

    private Users findById(String id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private UsersDto toUserDto(Users original) {
        var bean = new UsersDto();
        bean.setId(original.getId());
        bean.setName(original.getName());
        bean.setRoles(original.getRoles());
        bean.setEmail(original.getEmail());
        bean.setCreatedAt(original.getCreatedAt());
        bean.setUpdatedAt(original.getUpdatedAt());

        BeanUtils.copyProperties(original, bean);
        return bean;
    }
}
