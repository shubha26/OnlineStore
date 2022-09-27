package com.bullish.electronic.store.model;

import com.bullish.electronic.store.service.discount.Discount;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@SessionScope
public class Cart {

    private Long Id;
    private final Map<Product, AtomicInteger> products = new ConcurrentHashMap();
}
