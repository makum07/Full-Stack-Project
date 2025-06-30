// 📁 config/DataInitializer.java
package com.skillup.taskmanager.config;

import com.skillup.taskmanager.model.Role;
import com.skillup.taskmanager.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        // Create default roles if they don't exist
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
    }

    private void createRoleIfNotFound(String name) {
        if (!roleRepository.existsByName(name)) {
            Role role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }
}