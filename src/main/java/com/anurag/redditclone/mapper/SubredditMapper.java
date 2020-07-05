package com.anurag.redditclone.mapper;

import com.anurag.redditclone.dto.SubredditDto;
import com.anurag.redditclone.model.Post;
import com.anurag.redditclone.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by anurag on 9/6/20.
 */
@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto toSubredditDto(Subreddit subreddit);

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit toSubredditEntity(SubredditDto subredditDto);

    default Integer mapPosts(List<Post> posts) {
        return posts.size();
    }
}
