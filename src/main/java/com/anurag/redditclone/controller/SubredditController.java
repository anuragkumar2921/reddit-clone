package com.anurag.redditclone.controller;

import com.anurag.redditclone.dto.SubredditDto;
import com.anurag.redditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by anurag on 7/6/20.
 */
@RestController
@RequestMapping("api/subreddit")
@AllArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @GetMapping("/all")
    public ResponseEntity<List<SubredditDto>> fetchAllSubreddit() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.findAll());
    }

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subredditService.createSubreddit(subredditDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> fetchSubredditById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.fetchSubredditById(id));
    }
}
