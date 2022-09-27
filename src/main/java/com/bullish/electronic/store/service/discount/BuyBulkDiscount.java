package com.bullish.electronic.store.service.discount;

import com.bullish.electronic.store.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
public class BuyBulkDiscount implements Discount{ // Not ImplementedFully

    private Product product;
    private double benchPrice;
    private double discountPercent;

    public double getDiscountPrice(Map<Product, AtomicInteger> cart) {

        double sum = 0;
        Set<Product> products = cart.keySet();
        for(Product p : products){
           sum+=p.getPrice()* cart.get(p).get();
        }
        if (sum >= benchPrice) {
            return (100 - discountPercent) * sum / 100;
        }
        return sum;
    }
}
