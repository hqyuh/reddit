package com.hqyuh.springredditclone.controller;

import com.hqyuh.springredditclone.dto.SubredditDTO;
import com.hqyuh.springredditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDTO> createSubreddit(@RequestBody SubredditDTO subredditDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subredditService.saveSubreddit(subredditDto));
    }

    @PutMapping
    public ResponseEntity<SubredditDTO> updateSubreddit(@RequestBody SubredditDTO subredditDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.updateSubreddit(subredditDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubreddit(@PathVariable Long id){
        subredditService.deleteSubreddit(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SubredditDTO>> getAllSubreddit(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDTO> getSubreddit(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getSubreddit(id));
    }

}
