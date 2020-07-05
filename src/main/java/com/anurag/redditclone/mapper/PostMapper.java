package com.anurag.redditclone.mapper;

import com.anurag.redditclone.dto.PostRequest;
import com.anurag.redditclone.dto.PostResponse;
import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.Subreddit;
import com.anurag.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by anurag on 5/7/20.
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    Post mapDtoToEntity(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "username", source = "user.username")
    PostResponse mapEntityToDto(Post post);
}
