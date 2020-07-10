package com.anurag.redditclone.service;

import com.anurag.redditclone.dto.VoteDto;
import com.anurag.redditclone.exception.RedditCloneException;
import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.User;
import com.anurag.redditclone.model.Vote;
import com.anurag.redditclone.repository.PostRepository;
import com.anurag.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.anurag.redditclone.enums.VoteType.UP_VOTE;

/**
 * Created by anurag on 7/7/20.
 */
@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final AuthService authService;
    private final PostRepository postRepository;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findByPostId(voteDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("No post found for the postId : " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new RedditCloneException("You have already " + voteDto.getVoteType() + " for this post");
        }
        if (UP_VOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post, authService.getCurrentUser()));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post, User currentUser) {
        return Vote.builder()
                .post(post)
                .user(currentUser)
                .voteType(voteDto.getVoteType())
                .build();
    }
}
