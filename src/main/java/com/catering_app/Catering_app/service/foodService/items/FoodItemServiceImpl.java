package com.catering_app.Catering_app.service.foodService.items;

import com.catering_app.Catering_app.dto.FoodItemDto;
import com.catering_app.Catering_app.model.Categories;
import com.catering_app.Catering_app.model.Items;
import com.catering_app.Catering_app.model.OrderedItems;
import com.catering_app.Catering_app.repository.ItemsRepository;
import com.catering_app.Catering_app.repository.OrderedItemRepository;
import com.catering_app.Catering_app.service.categoryService.CategoriesService;
import com.catering_app.Catering_app.service.cloudinaryService.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

    private final ItemsRepository itemsRepository;
    private final CategoriesService categoriesService;
    private final OrderedItemRepository orderedItemRepository;
    private final CloudinaryService cloudinaryService;


    @Override
    public boolean addFoodItem(FoodItemDto fooItemDto, MultipartFile file) {

        Optional<Categories> optionalCategories =categoriesService.getCategoryById(fooItemDto.getCategoryId());
        if (optionalCategories.isPresent()){
            Categories category = optionalCategories.get();
            createNewFoodItem(fooItemDto, file, category);
            return true;
        }else {
            return false;
        }
    }

    private void createNewFoodItem(FoodItemDto fooItemDto, MultipartFile file, Categories category) {
        Items foodItem = new Items();
        foodItem.setItemName(fooItemDto.getItemName());
        foodItem.setItemPrice(fooItemDto.getItemPrice());
        Map<?,?> uploadFile = cloudinaryService.uploadImage(file,"FoodItem_images");
        String imageUrl = (String) uploadFile.get("url");
        String publicId = (String) uploadFile.get("public_id");
        foodItem.setImageUrl(imageUrl);
        foodItem.setImageId(publicId);
        foodItem.setCategories(category);
        itemsRepository.save(foodItem);
    }


    public List<Items> getAllFoodItems() {
        return itemsRepository.findAll();
    }

    @Override
    public Optional<Items> findByName(String itemName) {
        return itemsRepository.findByItemName(itemName);
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
            Optional<Items>existingItem = itemsRepository.findByItemName(foodItemDto.getItemName());
            if (existingItem.isPresent() && !existingItem.get().getId().equals(id)){
                return false;
            }
            foodItem.setItemName(foodItemDto.getItemName());
            foodItem.setItemPrice(foodItemDto.getItemPrice());
            itemsRepository.save(foodItem);
            return true;
        } else {
            return false;
        }

    }
}
