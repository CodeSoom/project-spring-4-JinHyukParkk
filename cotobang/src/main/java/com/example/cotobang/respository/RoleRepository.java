package com.example.cotobang.respository;

import com.example.cotobang.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByUserId(Long userId);
}
