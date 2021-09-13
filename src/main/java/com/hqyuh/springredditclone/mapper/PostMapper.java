package com.hqyuh.springredditclone.mapper;

import com.hqyuh.springredditclone.dto.PostRequest;
import com.hqyuh.springredditclone.dto.PostResponse;
import com.hqyuh.springredditclone.model.Post;
import com.hqyuh.springredditclone.model.Subreddit;
import com.hqyuh.springredditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);


    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.userName")
    PostResponse maptoDto(Post post);

}
