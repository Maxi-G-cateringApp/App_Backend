package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Integer> {

    Optional<Offer> findByOfferName(String offerName);

}
