package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.Items;
import com.catering_app.Catering_app.service.foodService.items.FoodItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addItems(@RequestParam("item") String itemJson,
                                      @RequestPart("file") MultipartFile file) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FoodItemDto foodItemDto = mapper.readValue(itemJson, FoodItemDto.class);
        Optional<Items> item = foodItemService.findByName(foodItemDto.getItemName());
        if (item.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Exist");
        } else {
            boolean addItem = foodItemService.addFoodItem(foodItemDto, file);
            ResponseDto response;
            if (addItem) {
                response = new ResponseDto(true, "add item success");
            } else {
                response = new ResponseDto(false, "add item success");
            }
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<Items>> getAllFoodItems() {
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    @PutMapping("/edit-item")
    public ResponseEntity<ResponseDto> editItem(@RequestParam Integer id,
                                                @RequestBody FoodItemDto foodItemDto) {
        boolean response = foodItemService.editFoodItem(id, foodItemDto);
        if (response) {
            return ResponseEntity.ok(new ResponseDto(true, "edit success"));
        } else {
            return ResponseEntity.ok(new ResponseDto(false, "something Wrong"));
        }
    }

    @GetMapping("/items-category")
    public ResponseEntity<List<Items>> getFoodItemByCategory(@RequestParam Integer id) {
        return ResponseEntity.ok(foodItemService.getItemByCategoryId(id));
    }

    @GetMapping("/get-item")
    public ResponseEntity<?> getItemById(@RequestParam("id") Integer id, HttpServletRequest httpServletRequest) {
        Optional<Items> optionalFoodItem = foodItemService.findById(id);
        if (optionalFoodItem.isPresent()) {
            Items foodItem = optionalFoodItem.get();
            return ResponseEntity.ok(foodItem);
        } else {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
    }

}




