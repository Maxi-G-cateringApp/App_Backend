package com.catering_app.Catering_app.service.paymentService;

import com.catering_app.Catering_app.model.TransactionDetails;
import com.catering_app.Catering_app.service.orderService.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private OrderService orderService;


    private static final String KEY = "rzp_test_J4LYX9mptrIIqg";
    private static final String KEY_SECRET = "XU4WWENN7SVKHOBr6zYKiTU4";
    private static final String CURRENCY = "INR";

    public TransactionDetails createTransaction(UUID orderId){
        Float total = orderService.getTotalAmount(orderId).getAmount();
       float amount = total * 25/100;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount",amount*100);
            jsonObject.put("currency",CURRENCY);
            RazorpayClient client = new RazorpayClient(KEY,KEY_SECRET);
            Order order = client.orders.create(jsonObject);
            return prepareTransactionDetails(order);
        } catch (RazorpayException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public TransactionDetails createBalanceTransaction(UUID orderId){
        com.catering_app.Catering_app.model.Order userOrder = orderService.getOrderById(orderId)
                .orElseThrow(()->new EntityNotFoundException("order not found"));
        Float total = userOrder.getTotalAmount();
        Float advancedAmount = userOrder.getAdvanceAmount();
        float balanceAmount = total- advancedAmount;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount",balanceAmount*100);
            jsonObject.put("currency",CURRENCY);
            RazorpayClient client = new RazorpayClient(KEY,KEY_SECRET);
            Order order = client.orders.create(jsonObject);
            return prepareTransactionDetails(order);
        } catch (RazorpayException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order){
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");
        return new TransactionDetails(orderId,currency,amount,KEY);

    }
}
