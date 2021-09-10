package com.hqyuh.springredditclone.repository;

import com.hqyuh.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}