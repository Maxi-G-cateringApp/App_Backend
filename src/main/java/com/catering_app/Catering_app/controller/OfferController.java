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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/create-offer")
    public ResponseEntity<?>createOffer(@RequestBody OfferDto offerDto){
        System.out.println(offerDto.getOfferName());
        System.out.println(offerDto.getDiscount());
        offerService.createOffer(offerDto);
        return ResponseEntity.ok(new ResponseDto(true,"offer added"));
    }
    @GetMapping("/get-offers")
    public ResponseEntity<List<Offer>>getAllOffers(){
        return ResponseEntity.ok(offerService.getAllOffers());
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
}
