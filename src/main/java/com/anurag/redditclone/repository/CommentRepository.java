package com.anurag.redditclone.repository;

import com.anurag.redditclone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by anurag on 1/6/20.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
