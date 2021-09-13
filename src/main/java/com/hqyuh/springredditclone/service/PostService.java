package com.hqyuh.springredditclone.service;

import com.hqyuh.springredditclone.dto.PostRequest;
import com.hqyuh.springredditclone.dto.PostResponse;
import com.hqyuh.springredditclone.exception.PostNotFoundException;
import com.hqyuh.springredditclone.exception.SubredditNotFoundException;
import com.hqyuh.springredditclone.mapper.PostMapper;
import com.hqyuh.springredditclone.model.Post;
import com.hqyuh.springredditclone.model.Subreddit;
import com.hqyuh.springredditclone.model.User;
import com.hqyuh.springredditclone.repository.PostRepository;
import com.hqyuh.springredditclone.repository.SubredditRepository;
import com.hqyuh.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // save
    public void savePost(PostRequest postRequest){

        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));

        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    // delete
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    // get one
    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));

        return postMapper.maptoDto(post);
    }

    // get all
    public List<PostResponse> getAllPost(){
        return postRepository
                .findAll()
                .stream()
                .map(postMapper::maptoDto)
                .collect(Collectors.toList());
    }

    // get post by subreddit
    public List<PostResponse> getPostBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));

        List<Post> posts = postRepository.findAllBySubreddit(subreddit);

        return posts
                .stream()
                .map(postMapper::maptoDto)
                .collect(Collectors.toList());
    }

    // get post by user
    public List<PostResponse> getPostByUser(String name){
        User user = userRepository.findByUserName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));

        return postRepository
                .findByUser(user)
                .stream()
                .map(postMapper::maptoDto)
                .collect(Collectors.toList());
    }

}
