package com.intellisense.ebookstorev1;

import com.intellisense.ebookstorev1.store.model.User;
import com.intellisense.ebookstorev1.store.model.security.Role;
import com.intellisense.ebookstorev1.store.model.security.UserRole;
import com.intellisense.ebookstorev1.store.service.UserService;
import com.intellisense.ebookstorev1.ultility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class EbookstoreV1Application implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(EbookstoreV1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("Ismael");
		user1.setLastName("Taiwo");
		user1.setUsername("superadmin");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("password100"));
		user1.setEmail("superadmin@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user1, role1));

		userService.createUser(user1, userRoles);
	}

}
