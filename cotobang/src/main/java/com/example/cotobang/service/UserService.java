package com.example.cotobang.service;

import com.example.cotobang.domain.Role;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.UserModificationDto;
import com.example.cotobang.dto.UserRegistrationDto;
import com.example.cotobang.errors.UserEmailDuplicationException;
import com.example.cotobang.errors.UserNotFoundException;
import com.example.cotobang.respository.RoleRepository;
import com.example.cotobang.respository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserRegistrationDto userRegistrationDto) {
        String email = userRegistrationDto.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserEmailDuplicationException(email);
        }

        User user = User.builder()
                .email(userRegistrationDto.getEmail())
                .name(userRegistrationDto.getName())
                .build();

        user.changePassword(
                userRegistrationDto.getPassword(),
                passwordEncoder
        );

        User savedUser = userRepository.save(user);
        Role role = Role.builder()
                .userId(user.getId())
                .name("USER")
                .build();
        roleRepository.save(role);

        return savedUser;
    }

    public User updateUser(Long id, UserModificationDto userModificationDto) {
        User user = getUser(id);

        user.change(userModificationDto.getName());
        user.changePassword(
                userModificationDto.getPassword(),
                passwordEncoder);

        return user;
    }

    public User delete(Long id) {
        User user = getUser(id);

        if (user.isDeleted()) {
            throw new UserNotFoundException(id);
        }

        user.destory();

        return user;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
