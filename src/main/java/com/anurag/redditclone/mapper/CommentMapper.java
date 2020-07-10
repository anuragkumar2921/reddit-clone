package com.anurag.redditclone.mapper;

import com.anurag.redditclone.dto.CommentsDto;
import com.anurag.redditclone.model.Comment;
import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by anurag on 6/7/20.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "text", source = "commentsDto.text")
    Comment mapToEntity(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
}
