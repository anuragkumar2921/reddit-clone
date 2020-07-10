package com.anurag.redditclone.controller;

import com.anurag.redditclone.dto.CommentsDto;
import com.anurag.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

/**
 * Created by anurag on 6/7/20.
 */
@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
@Slf4j
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentsDto commentsDto) {
        commentService.createComment(commentsDto);
        return new ResponseEntity(CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllComments(@PathVariable Long postId) {
        return status(OK).body(commentService.getAllComments(postId));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentsDto>> getCommentsByUsername(@PathVariable String username) {
        return status(OK).body(commentService.getCommentsByUsername(username));
    }
}
