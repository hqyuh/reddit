package com.hqyuh.springredditclone.repository;

import com.hqyuh.springredditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}