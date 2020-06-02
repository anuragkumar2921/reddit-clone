package com.anurag.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

/**
 * Created by anurag on 31/5/20.
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Subreddit name cannot be empty or null")
    private String name;
    @NotBlank(message = "description is required")
    @Lob
    private String description;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany
    private List<Post> posts;
}
