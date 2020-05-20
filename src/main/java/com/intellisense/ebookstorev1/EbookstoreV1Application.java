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
import java.util.List;
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
		User adminUser = new User();
		adminUser.setFirstName("Ismael");
		adminUser.setLastName("Taiwo");
		adminUser.setUsername("superadmin");
		adminUser.setPassword(SecurityUtility.passwordEncoder().encode("password100"));
		adminUser.setEmail("superadmin@gmail.com");

		Set<UserRole> userRoles = new HashSet<>();

		// create a new role
		Role adminRole = new Role();
		adminRole.setRoleId(1);
		adminRole.setName("ROLE_ADMIN");

		// add created role to user
		userRoles.add(new UserRole(adminUser, adminRole));

		// create a new user
		userService.createUser(adminUser, userRoles);
	}

}
