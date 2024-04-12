package com.catering_app.Catering_app.service.foodService.items;

import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.model.Items;
import com.catering_app.Catering_app.repository.ItemsRepository;
import com.catering_app.Catering_app.service.categoryService.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private CategoriesService categoriesService;

    @Override
    public boolean addFoodItem(FoodItemDto fooItemDto) {

        Optional<Categories> optionalCategories =categoriesService.getCategoryById(fooItemDto.getCategoryId());
        if (optionalCategories.isPresent()){
            Categories category = optionalCategories.get();
            Items foodItem = createFoodItem(fooItemDto);
            foodItem.setCategories(category);
            itemsRepository.save(foodItem);
            return true;
        }else {
            return false;
        }
    }

    private Items createFoodItem(FoodItemDto fooItemDto) {
        Items foodItem = new Items();
        foodItem.setItemName(fooItemDto.getItemName());
        foodItem.setItemPrice(fooItemDto.getItemPrice());
        return foodItem;
    }

    public List<Items> getAllFoodItems() {
        return itemsRepository.findAll();
    }

    @Override
    public Optional<Items> findByName(String itemName) {
        return itemsRepository.findByItemName(itemName);
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
    public List<Items> getItemByCategoryId(Integer id) {

        Optional<Categories> optionalCategories = categoriesService.findById(id);
        if (optionalCategories.isPresent()){
            Categories category = optionalCategories.get();
            if (category.getCategoriesName().equals("All")){
                return itemsRepository.findAll();
            }else{
                return itemsRepository.findFoodItemByCategories(category);
            }
        }else {
            return null;
        }
    }

    @Override
    public boolean editFoodItem(Integer id, FoodItemDto foodItemDto) {

        Optional<Items> optionalFoodItem = itemsRepository.findById(id);
        if (optionalFoodItem.isPresent()) {
            Items foodItem = optionalFoodItem.get();
//            foodItem.setCategories(foodItemDto.getCategory());
            foodItem.setItemName(foodItemDto.getItemName());
            foodItem.setItemPrice(foodItemDto.getItemPrice());
            itemsRepository.save(foodItem);
            return true;
        } else {
            return false;
        }

    }
}
