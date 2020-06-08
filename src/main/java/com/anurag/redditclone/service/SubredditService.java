package com.anurag.redditclone.service;

import com.anurag.redditclone.dto.SubredditDto;
import com.anurag.redditclone.model.Subreddit;
import com.anurag.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by anurag on 7/6/20.
 */
@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional(readOnly = true)
    public List<SubredditDto> findAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto
                .builder()
                .id(subreddit.getId())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .name(subreddit.getName())
                .build();
    }

    private Subreddit mapToEntity(SubredditDto subredditDto) {
        return Subreddit
                .builder()
                .description(subredditDto.getDescription())
                .name(subredditDto.getName())
                .build();
    }

    @Transactional
    public SubredditDto createSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(mapToEntity(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }
}
