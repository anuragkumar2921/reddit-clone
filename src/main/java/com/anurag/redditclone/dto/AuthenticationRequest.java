package com.anurag.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by anurag on 6/6/20.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String userName;
    private String password;
}
