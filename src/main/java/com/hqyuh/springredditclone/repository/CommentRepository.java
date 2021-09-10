package com.hqyuh.springredditclone.repository;

import com.hqyuh.springredditclone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}