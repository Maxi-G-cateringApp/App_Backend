package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedRepository extends JpaRepository<Feed,Long> {

    List<Feed> getFeedByUserId(UUID userId);

}
