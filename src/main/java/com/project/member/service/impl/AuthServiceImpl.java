package com.project.member.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.project.member.config.exception.CustomException;
import com.project.member.entity.Members;
import com.project.member.repository.MembersRepository;
import com.project.member.repository.RolesRepository;
import com.project.member.service.AuthService;
import com.project.member.vo.RegisterVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.project.member.util.MyConstants.*;
import static com.project.member.util.UtilFn.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RolesRepository rolesRepository;
    private final MembersRepository membersRepository;

    @Override
    public Map<Object, String> register(RegisterVo vo) {
        Map<Object, String> ret = new HashMap<>();
        try {
            log.info("checking data member by email");
            String email = vo.getEmail();
            Optional<Members> checkEmail = membersRepository.findByEmail(email);
            if (checkEmail.isPresent()) {
                log.error("email already used");
                throw new CustomException(EMAIL_ALREADY, HttpStatus.BAD_REQUEST);
            }
            log.info("file {}", vo.getPhotoUrl());
            var photo = uploadFile(vo.getPhotoUrl());
            Members member = new Members();
            member.setName(vo.getName());
            member.setUsername(createUsernameFromName(vo.getName()));
            member.setEmail(email);
            member.setPassword(new BCryptPasswordEncoder(12).encode(vo.getPassword()));
            member.setPhoneNumber(vo.getPhoneNumber());
            member.setDateOfBirth(vo.getDateOfBirth());
            member.setGender(vo.getGender());
            member.setIdentityNumber(vo.getIdentityNumber());
            member.setPhotoUrl(photo);
            member.setAddress(vo.getAddress());
            member.setRoles(rolesRepository.findByName("MEMBER"));
            member.setCreatedAt(LocalDateTime.now());
            member.setUpdatedAt(LocalDateTime.now());

            membersRepository.save(member);

            ret.put("status", SUCCESS);
            ret.put("message", "Success Register");

            log.info("Member registered successfully with email: {}", email);
        } catch (Exception e) {
            ret.put("status", ERROR);
            ret.put("message", "Failed to register member");

            log.error("Failed to register member", e);
        }
        return ret;
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
