package com.anurag.redditclone.repository;

import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.Subreddit;
import com.anurag.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by anurag on 1/6/20.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long id);
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findAllByUser(User user);
}
