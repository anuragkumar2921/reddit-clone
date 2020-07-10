package com.anurag.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Created by anurag on 6/7/20.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String username;
}
