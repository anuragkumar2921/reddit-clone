package com.anurag.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

/**
 * Created by anurag on 31/5/20.
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "User name cannot be null")
    private String username;
    @NotBlank(message = "Password cannot be null")
    private String password;
    @NotBlank(message = "Email cannot be null")
    private String email;
    private Instant createdDate;
    private Boolean enabled;
}
