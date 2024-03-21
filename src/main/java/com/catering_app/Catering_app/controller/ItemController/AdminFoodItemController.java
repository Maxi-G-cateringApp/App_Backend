package com.catering_app.Catering_app.controller.ItemController;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.service.foodService.FoodComboService;
import com.catering_app.Catering_app.service.foodService.FoodItemService;
import com.catering_app.Catering_app.service.jwtService.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
//@CrossOrigin("http://localhost:4200")
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
    public ResponseEntity<ResponseDto> addComboItems(@RequestBody FoodComboDto foodComboDto,
                                                     HttpServletRequest httpServletRequest) throws IOException {

        String token = httpServletRequest.getHeader("Authorization").split(" ")[1];
        if (jwtService.isTokenExpired(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else{
            boolean addItem = foodComboService.addFoodCombo(foodComboDto);
            ResponseDto response;
            if (addItem){
                response = new ResponseDto(true,"add item success");
            }else {
                response = new ResponseDto(false,"add item success");
            }
            return ResponseEntity.ok(response);
        }
    }
    @PostMapping("/add-item")
    public ResponseEntity<ResponseDto> addItems(@RequestBody FoodItemDto foodItemDto,
                                                HttpServletRequest httpServletRequest) {

        String token = httpServletRequest.getHeader("Authorization").split(" ")[1];
        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            boolean addItem = foodItemService.addFoodItem(foodItemDto);
            ResponseDto response;
            if (addItem){
                response = new ResponseDto(true,"add item success");
            }else {
                response = new ResponseDto(false,"add item success");
            }
            return ResponseEntity.ok(response);
        }

    }

    @PutMapping("/edit-combo")
    public ResponseEntity<ResponseDto>editCombo(@RequestParam Integer id,
                                                @RequestBody FoodComboDto foodComboDto){
        foodComboService.editFoodCombo(id,foodComboDto);
        return ResponseEntity.ok(new ResponseDto(true,"edit success"));

    }

    @PutMapping("/edit-item")
    public ResponseEntity<ResponseDto>editItem(@RequestParam Integer id,
                                                @RequestBody FoodItemDto foodItemDto){
        foodItemService.editFoodItem(id,foodItemDto);
        return ResponseEntity.ok(new ResponseDto(true,"edit success"));

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
}
