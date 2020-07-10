package com.anurag.redditclone.service;

import com.anurag.redditclone.dto.CommentsDto;
import com.anurag.redditclone.mapper.CommentMapper;
import com.anurag.redditclone.model.NotificationEmail;
import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.User;
import com.anurag.redditclone.repository.CommentRepository;
import com.anurag.redditclone.repository.PostRepository;
import com.anurag.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by anurag on 6/7/20.
 */
@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private static final String POST_URL = "";
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;
    private final UserRepository userRepository;

    public void createComment(CommentsDto commentsDto) {
        Post post = postRepository.findByPostId(commentsDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("No post found for the postId : " + commentsDto.getPostId()));
        commentRepository.save(commentMapper.mapToEntity(commentsDto, post, authService.getCurrentUser()));
        String message = mailContentBuilder.buildEmail(authService.getCurrentUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(authService.getCurrentUser(), message);
    }

    private void sendCommentNotification(User user, String message) {
        mailService.sendEmail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllComments(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new EntityNotFoundException("No post found for the postId : " + postId));
        return commentRepository.findAllByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getCommentsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found for the username : " + username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
