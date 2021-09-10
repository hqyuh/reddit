package com.hqyuh.springredditclone.repository;

import com.hqyuh.springredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}