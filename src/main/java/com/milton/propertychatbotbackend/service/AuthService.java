package com.milton.propertychatbotbackend.service;

import com.milton.propertychatbotbackend.config.JwtTokenProvider;
import com.milton.propertychatbotbackend.dto.LoginDto;
import com.milton.propertychatbotbackend.dto.RegisterDto;
import com.milton.propertychatbotbackend.exeptions.PostApiException;
import com.milton.propertychatbotbackend.models.Role;
import com.milton.propertychatbotbackend.models.User;
import com.milton.propertychatbotbackend.repository.RoleRepository;
import com.milton.propertychatbotbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Service
@Slf4j
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager, UserRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }


    public String register(RegisterDto registerDto) {

        // add check for username exists in database
        if(usersRepository.existsByUsername(registerDto.getUsername())){
            throw new PostApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if(usersRepository.existsByEmail(registerDto.getEmail())){
            throw new PostApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setFirstName(registerDto.getFirstName());
        user.setUsername(registerDto.getUsername());

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        usersRepository.save(user);

        return "User registered successfully!.";
    }


    private UserDetails createNewUser(String username, String password) {
        log.info("createNewUser");

        Function<String, String> passwordEncoding
                = passwordEncoder::encode;

        return org.springframework.security.core.userdetails.User.builder()
                .passwordEncoder(passwordEncoding)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
    }




}
