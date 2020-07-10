package com.anurag.redditclone.repository;

import com.anurag.redditclone.model.Comment;
import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by anurag on 1/6/20.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);
}
