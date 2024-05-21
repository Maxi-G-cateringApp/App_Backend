package com.catering_app.Catering_app.service.pdfService;

import com.catering_app.Catering_app.model.Order;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateSalesReportPdf(List<Order> orders,Double totalSales, Integer orderCount){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont titleFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
            PdfFont headerFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
            PdfFont bodyFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);

            Paragraph title = new Paragraph("Sales Report")
                    .setFont(titleFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            Paragraph summary = new Paragraph(String.format("Total Sales: ₹%s\nTotal Orders: %d", totalSales, orderCount))
                    .setFont(bodyFont)
                    .setFontSize(18)
                    .setMarginBottom(20);
            document.add(summary);

            float[] columnWidths = {3, 3, 3, 2, 2};
            Table table = new Table(UnitValue.createPercentArray(columnWidths))
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(20);

            table.addHeaderCell(new Paragraph("Order Event").setFont(headerFont).setFontSize(12).setBackgroundColor(new DeviceRgb(140, 221, 8)));
            table.addHeaderCell(new Paragraph("User").setFont(headerFont).setFontSize(12).setBackgroundColor(new DeviceRgb(140, 221, 8)));
            table.addHeaderCell(new Paragraph("Total Amount").setFont(headerFont).setFontSize(12).setBackgroundColor(new DeviceRgb(140, 221, 8)));
            table.addHeaderCell(new Paragraph("Order Status").setFont(headerFont).setFontSize(12).setBackgroundColor(new DeviceRgb(140, 221, 8)));
            table.addHeaderCell(new Paragraph("Date").setFont(headerFont).setFontSize(12).setBackgroundColor(new DeviceRgb(140, 221, 8)));

            for (Order order : orders) {
                table.addCell(new Paragraph(order.getEvents().getEventName()).setFont(bodyFont).setFontSize(12));
                table.addCell(new Paragraph(order.getUser().getName()).setFont(bodyFont).setFontSize(12));
                table.addCell(new Paragraph("₹" + order.getTotalAmount().toString()).setFont(bodyFont).setFontSize(12));
                table.addCell(new Paragraph(order.getStatus().toString()).setFont(bodyFont).setFontSize(12));
                table.addCell(new Paragraph(order.getOrderDate().toString()).setFont(bodyFont).setFontSize(12));
            }

            document.add(table);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF report", e);
        }

    }

}
