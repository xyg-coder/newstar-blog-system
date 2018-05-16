package com.xinyuan.blog.service;

import com.xinyuan.blog.domain.Vote;

public interface VoteService {

    Vote getVoteById(Long id);

    void removeVote(Long id);
}
