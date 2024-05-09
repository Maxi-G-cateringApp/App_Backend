package com.catering_app.Catering_app.service.feedService;

import com.catering_app.Catering_app.model.Feed;
import com.catering_app.Catering_app.model.User;
import com.catering_app.Catering_app.repository.FeedRepository;
import com.catering_app.Catering_app.service.authService.AuthenticationService;
import com.catering_app.Catering_app.service.cloudinaryService.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final CloudinaryService cloudinaryService;
    private final FeedRepository feedRepository;
    private final AuthenticationService authenticationService;


    @Override
    public Feed addFeed(String content, UUID userId, MultipartFile file) {
        Optional<User>optionalUser = authenticationService.getUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            try {
                if (file.isEmpty()) {
                    return null;
                }
                Map<?,?> uploadFile = cloudinaryService.uploadImage(file, "feed_images");
                String imageUrl = (String) uploadFile.get("url");
                String publicId = (String) uploadFile.get("public_id");
                Feed feed = new Feed();
                feed.setImageUrl(imageUrl);
                feed.setImageId(publicId);
                feed.setContent(content);
                feed.setCreatedAt(LocalDate.now());
                feed.setUser(user);
                feedRepository.save(feed);
                return feed;
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    @Override
    public List<Feed> getAllFeeds() {
        List<Feed> feedList = feedRepository.findAll();
        Collections.shuffle(feedList);
        return feedList;
    }



    @Override
    public List<Feed> getFeedByUser(UUID userId) {
        return feedRepository.getFeedByUserId(userId);
    }

    @Override
    public void deleteFeedById(Long id) {
        Optional<Feed> optionalFeed = feedRepository.findById(id);
        if (optionalFeed.isPresent()){
            Feed feed = optionalFeed.get();
            String imageId = feed.getImageId();
            try {
                cloudinaryService.delete(imageId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        feedRepository.deleteById(id);
    }
}
