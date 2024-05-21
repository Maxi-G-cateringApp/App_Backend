package com.catering_app.Catering_app.service.salesReport;

import com.catering_app.Catering_app.dto.SalesDto;
import com.catering_app.Catering_app.model.Order;
import com.catering_app.Catering_app.model.TimePeriod;

import java.time.LocalDate;
import java.util.List;

public interface SalesReportService {

    List<Order> getOrderByTimePeriod(TimePeriod timePeriod);
    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
    Double calculateTotalSales(List<Order>orders);
    SalesDto getSalesForOneDay();
    SalesDto getSalesForOneWeek();
    SalesDto getSalesForOneMonth();
    SalesDto getSalesForOneYear();
    public SalesDto getSalesForAllTime();

}
