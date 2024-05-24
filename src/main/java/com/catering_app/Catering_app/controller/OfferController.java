package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.OfferDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.Offer;
import com.catering_app.Catering_app.service.offerService.OfferService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/create-offer")
    public ResponseEntity<?>createOffer(@RequestBody OfferDto offerDto){
        offerService.createOffer(offerDto);
        return ResponseEntity.ok(new ResponseDto(true,"offer added"));
    }
    @GetMapping("/get-offers")
    public ResponseEntity<List<Offer>>getAllOffers(){
        return ResponseEntity.ok(offerService.getAllOffers());
    }
    @GetMapping("/get/enabled/offers")
    public ResponseEntity<List<Offer>>getAllEnabledOffers(){
        return ResponseEntity.ok(offerService.getAllEnabledOffers());
    }

    @PostMapping("/enable-offer")
    public ResponseEntity<?>enableOffer(@RequestParam Integer id){
        offerService.enableOffer(id);
        return ResponseEntity.ok(HttpStatus.SC_OK);
    }

    @PostMapping("/disable-offer")
    public ResponseEntity<?>disableOffer(@RequestParam Integer id){
        offerService.disableOffer(id);
        return ResponseEntity.ok(HttpStatus.SC_OK);
    }

    @PutMapping("/edit/offer")
    public ResponseEntity<ResponseDto>editOffer(@RequestParam Integer id,@RequestBody OfferDto offerDto){
        boolean response = offerService.updateOffer(id,offerDto);
        if(response){
            return ResponseEntity.ok(new ResponseDto(true,"updateSuccess"));
        }else{
            return ResponseEntity.ok(new ResponseDto(false,"Wrong"));
        }
    }

    @GetMapping("/get/offer")
    public ResponseEntity<Offer>getOfferById(@RequestParam Integer id){
        return ResponseEntity.ok(offerService.getOfferById(id));
    }
}
