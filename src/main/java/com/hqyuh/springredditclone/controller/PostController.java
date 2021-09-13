package com.hqyuh.springredditclone.controller;

import com.hqyuh.springredditclone.dto.PostRequest;
import com.hqyuh.springredditclone.dto.PostResponse;
import com.hqyuh.springredditclone.model.Post;
import com.hqyuh.springredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.savePost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPost(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getAllPost());
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPostBySubreddit(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable String name){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPostByUser(name));
    }

}
