package com.catering_app.Catering_app.service.foodService;

import com.catering_app.Catering_app.dto.FoodComboDto;
import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.model.FoodItemCombos;
import com.catering_app.Catering_app.model.Items;
import com.catering_app.Catering_app.repository.FoodItemComboRepository;
import com.catering_app.Catering_app.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemServiceImpl implements FoodItemService {
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    FoodItemComboRepository foodItemComboRepository;

    @Override
    public boolean addFoodItem(FoodItemDto fooItemDto) {
        Items foodItem = new Items();
        createFoodItem(fooItemDto, foodItem);
        return true;
    }

    private void createFoodItem(FoodItemDto fooItemDto, Items foodItem) {
        foodItem.setCategory(fooItemDto.getCategory());
        foodItem.setItemName(fooItemDto.getItemName());
        foodItem.setItemPrice(fooItemDto.getItemPrice());
        itemsRepository.save(foodItem);
    }

    public List<Items> getAllFoodItems() {
        return itemsRepository.findAll();
    }



    @Override
    public void deleteItemById(Integer id) {
        itemsRepository.deleteById(id);
    }

    @Override
    public Optional<Items> findById(Integer id) {
        return itemsRepository.findById(id);
    }

    @Override
    public boolean editFoodItem(Integer id, FoodItemDto foodItemDto) {

        Optional<Items> optionalFoodItem = itemsRepository.findById(id);
        if (optionalFoodItem.isPresent()) {
            Items foodItem = optionalFoodItem.get();
            foodItem.setCategory(foodItemDto.getCategory());
            foodItem.setItemName(foodItemDto.getItemName());
            foodItem.setItemPrice(foodItemDto.getItemPrice());
            itemsRepository.save(foodItem);
            return true;
        } else {
            return false;
        }

    }
}
