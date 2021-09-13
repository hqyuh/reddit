package com.hqyuh.springredditclone.service;

import com.hqyuh.springredditclone.dto.SubredditDTO;
import com.hqyuh.springredditclone.exception.SpringRedditException;
import com.hqyuh.springredditclone.mapper.SubredditMapper;
import com.hqyuh.springredditclone.model.Subreddit;
import com.hqyuh.springredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    public List<SubredditDTO> getAll(){
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDTO getSubreddit(Long id){
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

}
