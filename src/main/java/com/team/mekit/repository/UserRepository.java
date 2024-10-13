package com.team.mekit.repository;

import com.team.mekit.entities.Image;
import com.team.mekit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}
