package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.Items;
import com.catering_app.Catering_app.service.foodService.combo.FoodComboService;
import com.catering_app.Catering_app.service.foodService.items.FoodItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class FoodComboController {

    private final FoodComboService foodComboService;

    public FoodComboController(FoodComboService foodComboService) {
        this.foodComboService = foodComboService;
    }


    @PostMapping("/add-combo")
    public ResponseEntity<ResponseDto> addComboItems(@RequestParam("combo") String comboJson,
                                                     @RequestPart("file") MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FoodComboDto foodComboDto = mapper.readValue(comboJson, FoodComboDto.class);
        boolean addItem = foodComboService.addFoodCombo(foodComboDto, file);
        ResponseDto response;
        if (addItem) {
            response = new ResponseDto(true, "add item success");
        } else {
            response = new ResponseDto(false, "add item success");
        }
        return ResponseEntity.ok(response);

    }

    @GetMapping("/combos-category")
    public ResponseEntity<List<FoodItemCombos>> getFoodCombosByCategory(@RequestParam Integer id) {
        return ResponseEntity.ok(foodComboService.getCombosByCategoryId(id));
    }


    @PutMapping("/edit-combo")
    public ResponseEntity<ResponseDto> editCombo(@RequestParam Integer id,
                                                 @RequestBody FoodComboDto foodComboDto) {
        boolean response = foodComboService.editFoodCombo(id, foodComboDto);
        if (response) {
            return ResponseEntity.ok(new ResponseDto(true, "edit success"));
        } else {
            return ResponseEntity.ok(new ResponseDto(false, "something Wrong"));
        }
    }

    @GetMapping("/get-combo")
    public ResponseEntity<?> getComboById(@RequestParam("id") Integer id, HttpServletRequest httpServletRequest) {
        Optional<FoodItemCombos> optionalFoodItemCombos = foodComboService.findById(id);
        if (optionalFoodItemCombos.isPresent()) {
            FoodItemCombos foodItemCombos = optionalFoodItemCombos.get();
            return ResponseEntity.ok(foodItemCombos);
        } else {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-combos")
    public ResponseEntity<List<FoodItemCombos>> getAllCombos() {
        return ResponseEntity.ok(foodComboService.getAllCombos());
    }

    @GetMapping("/combos/no-offer")
    public ResponseEntity<List<FoodItemCombos>> getAllCombosWithoutOffers() {
        return ResponseEntity.ok(foodComboService.getAllCombosWithoutOffer());
    }


}
