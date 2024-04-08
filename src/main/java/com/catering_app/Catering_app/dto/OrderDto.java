package com.catering_app.Catering_app.dto;

import com.catering_app.Catering_app.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {


    private UUID userId;
    private List<FoodItemCombos> foodCombos;
    private List<Items> foodItems;
    private Integer eventId;
    private Date date;
    private Integer peopleCount;
    private Venue venue;
    private String timeFrom;
    private String timeTo;
    private String decorationOption;

    
}
