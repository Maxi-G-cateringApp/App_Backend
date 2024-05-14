package com.catering_app.Catering_app.service.offerService;

import com.catering_app.Catering_app.dto.OfferDto;
import com.catering_app.Catering_app.model.Offer;

import java.util.List;

public interface OfferService {

    void createOffer(OfferDto offerDto);

    List<Offer> getAllOffers();

    void enableOffer(Integer id);
    void disableOffer(Integer id);
}
