package com.intellisense.ebookstorev1.store.service;

import com.intellisense.ebookstorev1.store.model.User;
import com.intellisense.ebookstorev1.store.model.security.PasswordResetToken;
import com.intellisense.ebookstorev1.store.model.security.UserRole;

import java.util.Set;

public interface UserService {

    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(Long id);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);
}
