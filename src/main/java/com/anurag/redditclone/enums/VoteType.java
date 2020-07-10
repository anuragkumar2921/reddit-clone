package com.anurag.redditclone.enums;

import com.anurag.redditclone.exception.RedditCloneException;
import java.util.Arrays;

/**
 * Created by anurag on 31/5/20.
 */

public enum VoteType {
    UP_VOTE(1),
    DOWN_VOTE(-1);

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new RedditCloneException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
