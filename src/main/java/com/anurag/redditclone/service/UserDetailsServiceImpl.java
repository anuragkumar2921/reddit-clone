package com.anurag.redditclone.service;

import com.anurag.redditclone.exception.RedditCloneException;
import com.anurag.redditclone.model.User;
import com.anurag.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by anurag on 6/6/20.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        User user = optionalUser.orElseThrow(() -> new RedditCloneException("User not found for the user name : " + userName));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthority("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

}
