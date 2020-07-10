package com.anurag.redditclone.mapper;

import com.anurag.redditclone.dto.PostRequest;
import com.anurag.redditclone.dto.PostResponse;
import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.Subreddit;
import com.anurag.redditclone.model.User;
import com.anurag.redditclone.repository.CommentRepository;
import com.anurag.redditclone.repository.VoteRepository;
import com.anurag.redditclone.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by anurag on 5/7/20.
 */
@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post mapDtoToEntity(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(getCommentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapEntityToDto(Post post);

    public Integer getCommentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

    public String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
