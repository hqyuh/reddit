package com.hqyuh.springredditclone.service;

import com.hqyuh.springredditclone.dto.SubredditDTO;
import com.hqyuh.springredditclone.mapper.SubredditMapper;
import com.hqyuh.springredditclone.model.Subreddit;
import com.hqyuh.springredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    public SubredditDTO saveSubreddit(SubredditDTO subredditDto){
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());

        return subredditDto;
    }

    public void deleteSubreddit(Long id){
        subredditRepository.deleteById(id);
    }

    public SubredditDTO updateSubreddit(SubredditDTO subredditDto){
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        return subredditDto;
    }

}
