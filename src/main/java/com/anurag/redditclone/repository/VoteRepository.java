package com.anurag.redditclone.repository;

import com.anurag.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by anurag on 1/6/20.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
