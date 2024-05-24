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
        System.out.println(offerDto.getComboId());
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
        System.out.println("offer enabledd");
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
        System.out.println("offer disableddd");
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        offer.setEnabled(false);
        FoodItemCombos foodItemCombo = offer.getFoodItemCombo();
        foodItemCombo.setComboPrice(foodItemCombo.getComboPrice());
        foodComboService.save(foodItemCombo);
        offerRepository.save(offer);
    }

    @Override
    public boolean updateOffer(Integer id, OfferDto offerDto) {
        Optional<Offer>optionalOffer = offerRepository.findById(id);
        if (optionalOffer.isPresent()){
            Offer offer = optionalOffer.get();

            Optional<Offer> existingOffer = offerRepository.findByOfferName(offerDto.getOfferName());
            if (existingOffer.isPresent() && !existingOffer.get().getId().equals(id)){
                return false;
            }
            offer.setDiscount(offerDto.getDiscount());
            offer.setOfferName(offerDto.getOfferName());
            offerRepository.save(offer);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Offer getOfferById(Integer id) {
        Optional<Offer>optionalOffer = offerRepository.findById(id);
        if (optionalOffer.isPresent()){
            return optionalOffer.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public List<Offer> getAllEnabledOffers() {
        return offerRepository.findAll().stream().filter(Offer::isEnabled).toList();
    }
}
