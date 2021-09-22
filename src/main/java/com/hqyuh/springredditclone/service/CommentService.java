package com.hqyuh.springredditclone.service;

import com.hqyuh.springredditclone.dto.CommentsDTO;
import com.hqyuh.springredditclone.exception.PostNotFoundException;
import com.hqyuh.springredditclone.mapper.CommentMapper;
import com.hqyuh.springredditclone.model.Comment;
import com.hqyuh.springredditclone.model.NotificationEmail;
import com.hqyuh.springredditclone.model.Post;
import com.hqyuh.springredditclone.model.User;
import com.hqyuh.springredditclone.repository.CommentRepository;
import com.hqyuh.springredditclone.repository.PostRepository;
import com.hqyuh.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private static final String POST_URL = "";

    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;

    public void createComment(CommentsDTO commentsDTO){
        Post post = postRepository.findById(commentsDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDTO.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDTO, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUserName() +
                " posted a comment on your post." + POST_URL);
        sentCommentNotification(message, post.getUser());

    }

    private void sentCommentNotification(String message, User user){
        mailService.sendMail(new NotificationEmail(
                user.getUserName() + " commented on your post.",
                user.getEmail(),
                message));
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }

    public List<CommentsDTO> getAll(){
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDTO> getCommentByPostId(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDTO> getCommentByUser(String userName){
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
