package com.catering_app.Catering_app.controller.ItemController;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.dto.ResponseDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.service.foodcombo.FoodComboService;
import com.catering_app.Catering_app.service.jwtService.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:4200")
public class FoodComboController {

    private final FoodComboService foodComboService;
    private final JwtService jwtService;

    public FoodComboController(FoodComboService foodComboService, JwtService jwtService) {
        this.foodComboService = foodComboService;
        this.jwtService = jwtService;
    }


    @PostMapping("/add-combo")
    public ResponseEntity<ResponseDto> addComboItems(@RequestBody FoodComboDto foodComboDto,
                                                     HttpServletRequest httpServletRequest){
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

    @GetMapping("/all-combos")
    public ResponseEntity<List<FoodItemCombos>> getAllCombos(){
        return ResponseEntity.ok(foodComboService.getAllCombos());
    }
}
