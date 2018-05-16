package com.xinyuan.blog.repository;

import com.xinyuan.blog.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * vote repository
 * */
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
