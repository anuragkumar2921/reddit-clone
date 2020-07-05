package com.anurag.redditclone.service;

import com.anurag.redditclone.dto.SubredditDto;
import com.anurag.redditclone.exception.RedditCloneException;
import com.anurag.redditclone.mapper.SubredditMapper;
import com.anurag.redditclone.model.Subreddit;
import com.anurag.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by anurag on 7/6/20.
 */
@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional(readOnly = true)
    public List<SubredditDto> findAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::toSubredditDto)
                .collect(toList());
    }

    @Transactional
    public SubredditDto createSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.toSubredditEntity(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public SubredditDto fetchSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subreddit not found for the id: " + id));
        return subredditMapper.toSubredditDto(subreddit);
    }
}
