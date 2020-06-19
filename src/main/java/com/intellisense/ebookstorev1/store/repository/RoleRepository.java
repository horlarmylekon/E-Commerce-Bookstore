package com.intellisense.ebookstorev1.store.repository;

import com.intellisense.ebookstorev1.store.model.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
