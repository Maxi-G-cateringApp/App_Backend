package com.catering_app.Catering_app.dto;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalRevenue;
    private Integer totalOrderCount;
    private List<String> monthlyLabels;
    private List<Double> monthlyAmounts;
    private Map<LocalDate, Long> dailyOrderCounts;

    public void setMonthlySales(List<String> monthlyLabels, List<Double> monthlyAmounts) {
        this.monthlyLabels = monthlyLabels;
        this.monthlyAmounts = monthlyAmounts;

    }
    public void setDailyOrderCounts(Map<LocalDate, Long> dailyOrderCounts) {
        this.dailyOrderCounts = dailyOrderCounts;
    }



}
