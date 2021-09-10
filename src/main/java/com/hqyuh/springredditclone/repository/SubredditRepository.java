package com.hqyuh.springredditclone.repository;

import com.hqyuh.springredditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}