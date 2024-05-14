package com.catering_app.Catering_app.repository;

import com.catering_app.Catering_app.model.OrderedItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItems,Integer> {

    List<OrderedItems> findByFoodItems_Id(Integer id);
}
