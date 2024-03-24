package com.project.member.config.security;

import com.project.member.config.exception.CustomException;
import com.project.member.entity.Members;
import com.project.member.entity.Users;
import com.project.member.repository.MembersRepository;
import com.project.member.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.project.member.util.MyConstants.INVALID_EMAIl_OR_PASSWORD;
import static com.project.member.util.MyConstants.INVALID_NAME_OR_PASSWORD;

@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class CustomServiceConfig implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final MembersRepository membersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : {}", username);
        UserDetails userDetails;
        if (username.contains("@")) {
            log.info("check in member");
            Members member = membersRepository.findByEmail(username)
                    .orElseThrow(() -> new CustomException(INVALID_EMAIl_OR_PASSWORD, HttpStatus.BAD_REQUEST));

            userDetails = member;
        } else {
            log.info("check in user");
            Users user = usersRepository.findByName(username)
                    .orElseThrow(() -> new CustomException(INVALID_NAME_OR_PASSWORD, HttpStatus.BAD_REQUEST));

            userDetails = user;
        }
        return userDetails;
    }

}
