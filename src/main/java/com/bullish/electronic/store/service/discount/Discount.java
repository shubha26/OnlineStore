package com.bullish.electronic.store.service.discount;

import com.bullish.electronic.store.model.Product;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public interface Discount {
    double getDiscountPrice(Map<Product, AtomicInteger> cart);

    void setProduct(Product p);
}






