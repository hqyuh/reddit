package com.hqyuh.springredditclone.mapper;

import com.hqyuh.springredditclone.dto.CommentsDTO;
import com.hqyuh.springredditclone.model.Comment;
import com.hqyuh.springredditclone.model.Post;
import com.hqyuh.springredditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDTO.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment map(CommentsDTO commentsDTO, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUserName())")
    CommentsDTO mapToDto(Comment comment);
}
