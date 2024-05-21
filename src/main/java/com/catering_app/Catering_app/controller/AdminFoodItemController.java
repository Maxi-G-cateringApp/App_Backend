package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.Items;
import com.catering_app.Catering_app.service.foodService.combo.FoodComboService;
import com.catering_app.Catering_app.service.foodService.items.FoodItemService;
import com.catering_app.Catering_app.service.jwtService.JwtService;
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
@RequestMapping("/admin")
public class AdminFoodItemController {

    private final FoodComboService foodComboService;
    private final FoodItemService foodItemService;
    private final JwtService jwtService;

    public AdminFoodItemController(FoodComboService foodComboService, FoodItemService foodItemService, JwtService jwtService) {
        this.foodComboService = foodComboService;
        this.foodItemService = foodItemService;
        this.jwtService = jwtService;
    }


    @PostMapping("/add-combo")
    public ResponseEntity<ResponseDto> addComboItems(@RequestParam ("combo") String comboJson,
                                                     @RequestPart("file") MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FoodComboDto foodComboDto = mapper.readValue(comboJson, FoodComboDto.class);
            boolean addItem = foodComboService.addFoodCombo(foodComboDto,file);
            ResponseDto response;
            if (addItem){
                response = new ResponseDto(true,"add item success");
            }else {
                response = new ResponseDto(false,"add item success");
            }
            return ResponseEntity.ok(response);

    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addItems(@RequestBody FoodItemDto foodItemDto) {

        Optional<Items> item = foodItemService.findByName(foodItemDto.getItemName());
        if (item.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Data Exist");
        } else {
            boolean addItem = foodItemService.addFoodItem(foodItemDto);
            ResponseDto response;
            if (addItem) {
                response = new ResponseDto(true, "add item success");
            } else {
                response = new ResponseDto(false, "add item success");
            }
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/edit-combo")
    public ResponseEntity<ResponseDto>editCombo(@RequestParam Integer id,
                                                @RequestBody FoodComboDto foodComboDto){
            boolean response = foodComboService.editFoodCombo(id,foodComboDto);
            if(response){
                return ResponseEntity.ok(new ResponseDto(true,"edit success"));
            }else{
                return ResponseEntity.ok(new ResponseDto(false,"something Wrong"));
            }
    }

    @PutMapping("/edit-item")
    public ResponseEntity<ResponseDto>editItem(@RequestParam Integer id,
                                                @RequestBody FoodItemDto foodItemDto){
        boolean response = foodItemService.editFoodItem(id,foodItemDto);
        if (response){
            return ResponseEntity.ok(new ResponseDto(true,"edit success"));
        }else{
            return ResponseEntity.ok(new ResponseDto(false,"something Wrong"));
        }


    }

    @DeleteMapping("/delete-combo")
    public ResponseEntity<ResponseDto>deleteCombo(@RequestParam Integer id){
        foodComboService.deleteComboById(id);
        return  ResponseEntity.ok(new ResponseDto(true,"delete success"));
    }

    @DeleteMapping("/delete-item")
    public ResponseEntity<ResponseDto>deleteItem(@RequestParam Integer id){
        foodItemService.deleteItemById(id);
        return  ResponseEntity.ok(new ResponseDto(true,"delete success"));
    }

    @GetMapping("/all-combos")
    public ResponseEntity<List<FoodItemCombos>> getAllCombos(){
        return ResponseEntity.ok(foodComboService.getAllCombos());
    }
}
