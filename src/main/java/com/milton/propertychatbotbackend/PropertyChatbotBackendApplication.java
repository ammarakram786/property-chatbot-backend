package com.milton.propertychatbotbackend;

import com.milton.propertychatbotbackend.models.Role;
import com.milton.propertychatbotbackend.models.User;
import com.milton.propertychatbotbackend.repository.RoleRepository;
import com.milton.propertychatbotbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PropertyChatbotBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyChatbotBackendApplication.class, args);
    }


    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if roles exist, if not create them
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                Role userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
            }

            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            // Check if test user exists, if not create one
            if (!userRepository.existsByUsername("testuser")) {
                User testUser = new User();
                testUser.setUsername("testuser");
                testUser.setEmail("testuser@example.com");
                testUser.setPassword(passwordEncoder.encode("password"));
                testUser.setFirstName("Test");
                testUser.setRoles(new ArrayList<>(List.of(roleRepository.findByName("ROLE_USER").get())));
                userRepository.save(testUser);
            }
        };

    }
}
