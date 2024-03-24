package com.project.member.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.project.member.config.exception.CustomException;
import com.project.member.dto.MembersDto;
import com.project.member.entity.Members;
import com.project.member.repository.MembersRepository;
import com.project.member.repository.RolesRepository;
import com.project.member.service.MembersService;
import com.project.member.vo.MembersAddVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.project.member.util.MyConstants.*;
import static com.project.member.util.UtilFn.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MembersService {
    private final MembersRepository membersRepository;
    private final RolesRepository rolesRepository;

    @Override
    public Map<Object, String> save(MembersAddVo vo) {
        Map<Object, String> ret = new HashMap<>();
        try {
            log.info("checking data member add or update");
            Optional<Members> existingMember = vo.getId() != null ? membersRepository.findById(vo.getId()) : Optional.empty();
            String email = vo.getEmail();

            log.info("checking data member by email");
            if (existingMember.isPresent() && !existingMember.get().getEmail().equals(email)) {
                Optional<Members> checkName = membersRepository.findByEmail(email);
                if (checkName.isPresent()) {
                    log.error("email already used");
                    throw new CustomException(NAME_ALREADY, HttpStatus.BAD_REQUEST);
                }
            }
            log.info("file {}", vo.getPhotoUrl());
            log.info("file {}", vo.getPhotoUrl());
            var photo = uploadFile(vo.getPhotoUrl());
            Members member = existingMember.orElse(new Members());
            member.setName(vo.getName());
            member.setEmail(email);
            if (vo.getId() == null || existingMember.isEmpty()) {
                member.setUsername(createUsernameFromName(vo.getName()));
                member.setPassword(new BCryptPasswordEncoder(12).encode(vo.getPassword()));
                member.setCreatedAt(LocalDateTime.now());
                member.setRoles(rolesRepository.findByName("MEMBER"));
            }
            member.setPhoneNumber(vo.getPhoneNumber());
            member.setDateOfBirth(vo.getDateOfBirth());
            member.setGender(vo.getGender());
            member.setIdentityNumber(vo.getIdentityNumber());
            member.setPhotoUrl(photo);
            member.setAddress(vo.getAddress());
            member.setUpdatedAt(LocalDateTime.now());

            membersRepository.save(member);
            ret.put("status", SUCCESS);
            ret.put("message", "Success add or update member");

            log.info("member add or update successfully with email: {}", email);
        } catch (Exception e) {
            ret.put("status", ERROR);
            ret.put("message", "Failed to add or update member");

            log.error("Failed to add or update member", e);
        }
        return ret;
    }

    @Override
    public MembersDto getById(String id) {
        return toMemberDto(findById(id));
    }

    @Override
    public Page<MembersDto> list(Pageable pageable) {
        return membersRepository.getAll(pageable).map(this::toMemberDto);
    }

    @Override
    public Page<MembersDto> getListByName(String name, Pageable pageable) {
        return membersRepository.getListByName(name, pageable).map(this::toMemberDto);
    }

    @Override
    public Map<Object, String> delete(String id) {
        Map<Object, String> ret = new HashMap<>();
        try {
            var member = findById(id);
            member.setRoles(Collections.emptyList());
            membersRepository.delete(member);

            ret.put("status", SUCCESS);
            ret.put("message", "Success Delete member");

            log.info("user delete successfully with name: {}", member.getName());
        } catch (Exception e) {
            ret.put("status", ERROR);
            ret.put("message", "Failed to delete member");

            log.error("Failed to delete member", e);
        }
        return ret;
    }

    private Members findById(String id) {
        return membersRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private MembersDto toMemberDto(Members original) {
        var bean = new MembersDto();
        bean.setId(original.getId());
        bean.setName(original.getName());
        bean.setEmail(original.getEmail());
        bean.setPhoneNumber(original.getPhoneNumber());
        bean.setDateOfBirth(original.getDateOfBirth());
        bean.setGender(String.valueOf(original.getGender()));
        bean.setIdentityNumber(original.getIdentityNumber());
        bean.setPhotoUrl(original.getPhotoUrl());
        bean.setAddress(original.getAddress());
        bean.setRoles(original.getRoles());
        bean.setCreatedAt(original.getCreatedAt());
        bean.setUpdatedAt(original.getUpdatedAt());

        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        if (contentType == null) {
            throw new IOException("Could not determine content type of file: " + fileName);
        }

        BlobId blobId = BlobId.of("poto", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase/storage-ff1df-firebase-adminsdk-qtm5r-0720b08e42.json.example");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, file.getBytes());

        return URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    }
}
