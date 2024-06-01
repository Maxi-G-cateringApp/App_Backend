package com.catering_app.Catering_app.controller;

import com.catering_app.Catering_app.dto.Dates;
import com.catering_app.Catering_app.dto.SalesDto;
import com.catering_app.Catering_app.dto.SalesReportDto;
import com.catering_app.Catering_app.model.Order;
import com.catering_app.Catering_app.model.TimePeriod;
import com.catering_app.Catering_app.service.pdfService.PdfService;
import com.catering_app.Catering_app.service.salesReport.SalesReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SalesController {

    private final SalesReportService salesReportService;
    private final PdfService pdfService;

    public SalesController(SalesReportService salesReportService, PdfService pdfService) {
        this.salesReportService = salesReportService;
        this.pdfService = pdfService;
    }

    @GetMapping("/show/total-sale/graph")
    public ResponseEntity<SalesDto> getSaleForOneMonth(){
        return ResponseEntity.ok(salesReportService.getSalesForAllTime());

    }
    @GetMapping("/show/graph")
    public ResponseEntity<SalesDto>showGraph(@RequestParam TimePeriod timePeriod){

        SalesDto salesDto = null;
        switch (timePeriod){
            case DAILY -> salesDto = salesReportService.getSalesForOneDay();
            case MONTHLY -> salesDto = salesReportService.getSalesForOneMonth();
            case WEEKLY -> salesDto = salesReportService.getSalesForOneWeek();
            case YEARLY -> salesDto = salesReportService.getSalesForOneYear();
        }
        return ResponseEntity.ok(salesDto);
    }

    @GetMapping("/sales-report/timePeriod")
    public ResponseEntity<SalesReportDto>getSalesReport(@RequestParam TimePeriod timePeriod){

        List<Order> orders = salesReportService.getOrderByTimePeriod(timePeriod);
        Double totalSales = salesReportService.calculateTotalSales(orders);
        Integer orderCount = orders.size();
        SalesReportDto salesReportDto = SalesReportDto.builder()
                .orderList(orders)
                .totalSales(totalSales)
                .orderCount(orderCount)
                .build();
        return ResponseEntity.ok(salesReportDto);
    }
    @PostMapping("/sales-report/date")
    public ResponseEntity<SalesReportDto>getSalesReportByDates(@RequestBody Dates dates){

        List<Order> orders = salesReportService.findByOrderDateBetween(dates.getStartDate(),dates.getEndDate());
        Double totalSales = salesReportService.calculateTotalSales(orders);
        Integer orderCount = orders.size();
        SalesReportDto salesReportDto = SalesReportDto.builder()
                .orderList(orders)
                .totalSales(totalSales)
                .orderCount(orderCount)
                .build();
        return ResponseEntity.ok(salesReportDto);
    }

    @GetMapping("/sales-report/download")
    public ResponseEntity<byte[]> downloadSalesReport(@RequestParam TimePeriod timePeriod) {
        List<Order> orders = salesReportService.getOrderByTimePeriod(timePeriod);
        Double totalSales = salesReportService.calculateTotalSales(orders);
        Integer orderCount = orders.size();
        byte[] pdfBytes = pdfService.generateSalesReportPdf(orders, totalSales, orderCount);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "sales-report.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @PostMapping("/sales-report/dates/download")
    public ResponseEntity<byte[]> downloadSalesReportByDates(@RequestBody Dates dates) {
        System.out.println(dates.getStartDate());
        List<Order> orders = salesReportService.findByOrderDateBetween(dates.getStartDate(),dates.getEndDate());
        Double totalSales = salesReportService.calculateTotalSales(orders);
        Integer orderCount = orders.size();

        byte[] pdfBytes = pdfService.generateSalesReportPdf(orders, totalSales, orderCount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "sales-report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
