package com.catering_app.Catering_app.controller.ItemController;

import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.Items;
import com.catering_app.Catering_app.service.foodService.FoodComboService;
import com.catering_app.Catering_app.service.foodService.FoodItemService;
import com.catering_app.Catering_app.service.jwtService.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/admin")
public class FoodItemController {

    private final FoodItemService foodItemService;
    private final FoodComboService foodComboService;


    public FoodItemController(FoodItemService foodItemService, FoodComboService foodComboService) {
        this.foodItemService = foodItemService;
        this.foodComboService = foodComboService;
    }


    @GetMapping("/items")
    public ResponseEntity<List<Items>> getAllFoodItems(){
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    @GetMapping("/all-combos")
    public ResponseEntity<List<FoodItemCombos>> getAllCombos(){
        return ResponseEntity.ok(foodComboService.getAllCombos());
    }

    @GetMapping("/get-combo")
    public ResponseEntity<?>getComboById(@RequestParam("id") Integer id, HttpServletRequest httpServletRequest){
        Optional<FoodItemCombos> optionalFoodItemCombos = foodComboService.findById(id);
        if (optionalFoodItemCombos.isPresent()){
            FoodItemCombos foodItemCombos = optionalFoodItemCombos.get();
            return ResponseEntity.ok(foodItemCombos);
        }else{
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-item")
    public ResponseEntity<?>getItemById(@RequestParam("id") Integer id, HttpServletRequest httpServletRequest){
        Optional<Items> optionalFoodItem = foodItemService.findById(id);
        if (optionalFoodItem.isPresent()){
            Items foodItem = optionalFoodItem.get();
            return ResponseEntity.ok(foodItem);
        }else{
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
    }

}




