package com.project.member;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class MemberApplicationTests {

	@Test
	void contextLoads() {
		var generate = new BCryptPasswordEncoder(12).encode("password");
		System.out.println(generate);
	}

}
