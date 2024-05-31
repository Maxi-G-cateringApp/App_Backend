package com.catering_app.Catering_app.service.feedService;

import com.catering_app.Catering_app.dto.FeedDto;
import com.catering_app.Catering_app.model.Feed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.util.List;
import java.util.UUID;

public interface FeedService {

    Feed addFeed(String content, UUID userId, MultipartFile file);
    List<Feed> getAllFeeds();
    List<Feed>getFeedByUser(UUID userId);
    void deleteFeedById(Long id);

}
