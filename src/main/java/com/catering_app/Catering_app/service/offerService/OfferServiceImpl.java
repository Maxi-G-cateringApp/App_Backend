package com.catering_app.Catering_app.service.offerService;

import com.catering_app.Catering_app.dto.OfferDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.Offer;
import com.catering_app.Catering_app.repository.OfferRepository;
import com.catering_app.Catering_app.service.foodService.combo.FoodComboService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final FoodComboService foodComboService;
    private final OfferRepository offerRepository;

    @Override
    public void createOffer(OfferDto offerDto) {
        Optional<FoodItemCombos> optionalFoodItemCombos = foodComboService.findById(offerDto.getComboId());
        if (optionalFoodItemCombos.isPresent()) {
            FoodItemCombos foodItemCombo = optionalFoodItemCombos.get();
            Offer offer = new Offer();
            offer.setOfferName(offerDto.getOfferName());
            offer.setFoodItemCombo(foodItemCombo);
            offer.setDiscount(offerDto.getDiscount());
            foodItemCombo.setOffer(offer);
            offerRepository.save(offer);
            foodComboService.save(foodItemCombo);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public void enableOffer(Integer id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);

        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();


            FoodItemCombos foodItemCombo = offer.getFoodItemCombo();
            System.out.println(foodItemCombo.getOffer()+ "offer");
            float discount = (float) (foodItemCombo.getOffer().getDiscount() / 100.0) * foodItemCombo.getComboPrice();
            foodItemCombo.setOfferPrice(foodItemCombo.getComboPrice() - discount);
            offer.setEnabled(true);

            offerRepository.save(offer);
            foodComboService.save(foodItemCombo);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void disableOffer(Integer id) {
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        offer.setEnabled(false);
        FoodItemCombos foodItemCombo = offer.getFoodItemCombo();
        foodItemCombo.setComboPrice(foodItemCombo.getComboPrice());
        foodComboService.save(foodItemCombo);
        offerRepository.save(offer);
    }
}
