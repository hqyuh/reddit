package com.hqyuh.springredditclone.controller;

import com.hqyuh.springredditclone.dto.CommentsDTO;
import com.hqyuh.springredditclone.model.Comment;
import com.hqyuh.springredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentsDTO> createComment(@RequestBody CommentsDTO commentsDTO){
        commentService.createComment(commentsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDTO>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentByPostId(postId));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<CommentsDTO>> getAllCommentsForUser(@PathVariable String username){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentByUser(username));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentsDTO>> getAllComments(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getAll());
    }

}
