package com.anurag.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by anurag on 12/6/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String postId;
    private String subredditName;
    private String postName;
    private String description;
    private String url;
}
