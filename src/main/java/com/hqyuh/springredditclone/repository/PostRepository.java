package com.hqyuh.springredditclone.repository;

import com.hqyuh.springredditclone.model.Post;
import com.hqyuh.springredditclone.model.Subreddit;
import com.hqyuh.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);

}