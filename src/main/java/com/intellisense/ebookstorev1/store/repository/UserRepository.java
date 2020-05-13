package com.intellisense.ebookstorev1.store.repository;

import com.intellisense.ebookstorev1.store.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
