package com.anurag.redditclone.controller;

import com.anurag.redditclone.dto.PostRequest;
import com.anurag.redditclone.dto.PostResponse;
import com.anurag.redditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

/**
 * Created by anurag on 12/6/20.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
        return new ResponseEntity(CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return status(OK).body(postService.getPost(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(OK).body(postService.getAllPosts());
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long id) {
        return status(OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable String username) {
        return status(OK).body(postService.getPostsByUsername(username));
    }
}
