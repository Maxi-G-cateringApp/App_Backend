package com.catering_app.Catering_app.service.offerService;

import com.catering_app.Catering_app.dto.OfferDto;
import com.catering_app.Catering_app.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    void createOffer(OfferDto offerDto);

    List<Offer> getAllOffers();

    void enableOffer(Integer id);
    void disableOffer(Integer id);

    boolean updateOffer(Integer id, OfferDto offerDto);

   Offer getOfferById(Integer id);

    List<Offer> getAllEnabledOffers();
}
