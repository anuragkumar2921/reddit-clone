package com.anurag.redditclone.service;

import com.anurag.redditclone.dto.PostRequest;
import com.anurag.redditclone.dto.PostResponse;
import com.anurag.redditclone.mapper.PostMapper;
import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.Subreddit;
import com.anurag.redditclone.model.User;
import com.anurag.redditclone.repository.PostRepository;
import com.anurag.redditclone.repository.SubredditRepository;
import com.anurag.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by anurag on 13/6/20.
 */
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createPost(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository
                .findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new EntityNotFoundException("No subreddit found for the username : " + postRequest.getPostName()));
        User currentUser = authService.getCurrentUser();
        postRepository.save(postMapper.mapDtoToEntity(postRequest, subreddit, currentUser));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository
                .findByPostId(id)
                .orElseThrow(() -> new EntityNotFoundException("No post found for the postId : " + id.toString()));
        return postMapper.mapEntityToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No subreddit found for the Id : " + id.toString()));
        return postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(postMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found by the username : " + username));
        return postRepository.findAllByUser(user)
                .stream()
                .map(postMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
