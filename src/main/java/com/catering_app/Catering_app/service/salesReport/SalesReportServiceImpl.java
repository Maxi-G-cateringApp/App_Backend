package com.catering_app.Catering_app.service.salesReport;

import com.catering_app.Catering_app.dto.SalesDto;
import com.catering_app.Catering_app.model.Order;
import com.catering_app.Catering_app.model.Status;
import com.catering_app.Catering_app.model.TimePeriod;
import com.catering_app.Catering_app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {

    private final OrderRepository orderRepository;


    @Override
    public List<Order> getOrderByTimePeriod(TimePeriod timePeriod) {
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        switch (timePeriod) {
            case DAILY -> {
                startDate = endDate;
                break;
            }
            case WEEKLY -> {
                startDate = endDate.minusDays(6);
                break;
            }
            case MONTHLY -> {
                startDate = endDate.minusMonths(1);
                break;
            }
            case YEARLY -> {
                startDate = endDate.minusYears(1);
                break;
            }
            default -> {
                throw new IllegalArgumentException("unsupported time period " + timePeriod);
            }
        }
        return orderRepository.findByOrderDateBetween(startDate,endDate).stream().filter(order -> order.getStatus()!=Status.PENDING).toList();
    }
    @Override
    public List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByOrderDateBetween(startDate,endDate).stream().filter(order -> order.getStatus()!=Status.PENDING).toList();
    }

    public Double calculateTotalSales(List<Order>orders){
        double totalPrice = 0;
        for(Order order: orders){
            totalPrice += order.getTotalAmount() == null ? 0.0: order.getTotalAmount();
        }
        return totalPrice;
    }
    public SalesDto getSalesForOneDay(){
        LocalDate yesterday = LocalDate.now();
        LocalDate today = LocalDate.now();

        List<Order> orderListForOneDay = orderRepository.findByOrderDateBetween(yesterday,today)
                .stream().filter(order -> order.getStatus()!= Status.CANCELLED).toList();

        Map<LocalDate,Long> dailyOrderCount = orderListForOneDay.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate,Collectors.counting()));
        Double totalRevenue = orderListForOneDay.stream().mapToDouble(
                order -> order.getTotalAmount() == null ? 0.0: order.getTotalAmount()).sum();
        Integer totalCount = orderListForOneDay.size();

        return getSalesDto(yesterday, today, dailyOrderCount, totalRevenue, totalCount);

    }

    private static SalesDto getSalesDto(LocalDate yesterday, LocalDate today, Map<LocalDate, Long> dailyOrderCount, Double totalRevenue, Integer totalCount) {
        SalesDto salesDto = new SalesDto();
        salesDto.setStartDate(yesterday);
        salesDto.setEndDate(today);
        salesDto.setDailyOrderCounts(dailyOrderCount);
        salesDto.setTotalRevenue(totalRevenue);
        salesDto.setTotalOrderCount(totalCount);
        return salesDto;
    }

    public SalesDto getSalesForOneWeek(){
        LocalDate oneWeekAgo =  LocalDate.now().minusWeeks(1);
        LocalDate today = LocalDate.now();
        List<Order> orderListForOneWeek = orderRepository.findByOrderDateBetween(oneWeekAgo,today)
                .stream().filter(order -> order.getStatus()!= Status.CANCELLED && order.getStatus()!=Status.PENDING).toList();
        Map<LocalDate,Long> dailyOrderCount = orderListForOneWeek.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate,Collectors.counting()));
        Double totalRevenue = orderListForOneWeek.stream().mapToDouble(
                order -> order.getTotalAmount() == null ? 0.0: order.getTotalAmount()).sum();
        Integer totalCount = orderListForOneWeek.size();
        return getSalesDto(oneWeekAgo, today, dailyOrderCount, totalRevenue, totalCount);
    }
    public SalesDto getSalesForOneMonth(){
        LocalDate oneMonthAgo =  LocalDate.now().minusMonths(1);
        LocalDate today = LocalDate.now();
        List<Order> orderListForOneMonth = orderRepository.findByOrderDateBetween(oneMonthAgo,today)
                .stream().filter(order -> order.getStatus()!= Status.CANCELLED && order.getStatus()!=Status.PENDING).toList();
        Map<LocalDate,Long> dailyOrderCount = orderListForOneMonth.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate,Collectors.counting()));
        Double totalRevenue = orderListForOneMonth.stream().mapToDouble(
                order -> order.getTotalAmount() == null ? 0.0: order.getTotalAmount()).sum();
        Integer totalCount = orderListForOneMonth.size();
        return getSalesDto(oneMonthAgo, today, dailyOrderCount, totalRevenue, totalCount);
    }
    public SalesDto getSalesForOneYear(){
        LocalDate oneYearAgo =  LocalDate.now().minusYears(1);
        LocalDate today = LocalDate.now();
        List<Order> orderListForOneYear = orderRepository.findByOrderDateBetween(oneYearAgo,today)
                .stream().filter(order -> order.getStatus()!= Status.CANCELLED && order.getStatus()!=Status.PENDING).toList();
        Map<LocalDate,Long> dailyOrderCount = orderListForOneYear.stream()
                .collect(Collectors.groupingBy(Order::getOrderDate,Collectors.counting()));
        Double totalRevenue = orderListForOneYear.stream().mapToDouble(
                order -> order.getTotalAmount() == null ? 0.0: order.getTotalAmount()).sum();
        Integer totalCount = orderListForOneYear.size();
        return getSalesDto(oneYearAgo, today, dailyOrderCount, totalRevenue, totalCount);
    }

    public SalesDto getSalesForAllTime() {

        List<Order> totalSales = orderRepository.findAll().stream().filter(order ->
                order.getStatus()!= Status.CANCELLED && order.getStatus()!=Status.PENDING).toList();
        Map<String ,Double>monthlySales = new HashMap<>();

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM yyyy");
        for (Order order : totalSales){
            LocalDate orderDate = order.getOrderDate();
            String monthYear = orderDate.format(monthFormatter);
            double totalPrice = order.getTotalAmount() == null ? 0.0: order.getTotalAmount();
            monthlySales.put(monthYear,monthlySales.getOrDefault(monthYear, 0.0)+totalPrice);
        }
        List<String> months = new ArrayList<>(monthlySales.keySet());
        List<Double> amount  = new ArrayList<>(monthlySales.values());

        Double totalRevenue = amount.stream().mapToDouble(Double::doubleValue).sum();
        Integer totalCount = totalSales.size();

        return getSalesDto(months, amount, totalRevenue, totalCount);

    }
    private static SalesDto getSalesDto(List<String> months, List<Double> amount,
                                        Double totalRevenue, Integer totalCount) {
        SalesDto salesDto = new SalesDto();
        salesDto.setTotalOrderCount(totalCount);
        salesDto.setTotalRevenue(totalRevenue);
        salesDto.setMonthlySales(months, amount);
        return salesDto;
    }


}
