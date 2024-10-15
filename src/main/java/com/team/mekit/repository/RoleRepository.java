package com.team.mekit.repository;

import com.team.mekit.entities.Role;
import com.team.mekit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);

}
