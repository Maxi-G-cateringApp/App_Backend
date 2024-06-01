package com.catering_app.Catering_app.controller;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.Feed;
import com.catering_app.Catering_app.service.feedService.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping(value = "/add-feed")
    public ResponseEntity<?>addFeed(@RequestParam("content") String content,
                                    @RequestParam("userId") UUID userId,
                                    @RequestPart("file") MultipartFile file){
        return ResponseEntity.ok(feedService.addFeed(content,userId,file));
    }

    @GetMapping(value = "/get-feeds")
    public ResponseEntity<List<Feed>> getAllFeeds(){
        return ResponseEntity.ok(feedService.getAllFeeds());
    }

    @GetMapping(value = "/get-feeds/user")
    public ResponseEntity<List<Feed>>getFeedsBtyUser(@RequestParam("userId")UUID userId){
        return ResponseEntity.ok(feedService.getFeedByUser(userId));
    }

    @DeleteMapping("/delete-feed")
    public ResponseEntity<ResponseDto>deleteFeed(@RequestParam Long id){
        feedService.deleteFeedById(id);
        return ResponseEntity.ok(new ResponseDto(true,"delete success"));
    }
}
