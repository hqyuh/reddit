package com.hqyuh.springredditclone.repository;

import com.hqyuh.springredditclone.model.Comment;
import com.hqyuh.springredditclone.model.Post;
import com.hqyuh.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);


}