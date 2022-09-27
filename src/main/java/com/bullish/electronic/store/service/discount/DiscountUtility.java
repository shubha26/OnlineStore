package com.bullish.electronic.store.service.discount;

import com.bullish.electronic.store.model.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class DiscountUtility {
    public static Map<String,Discount> availableDiscounts = new HashMap();
    {
        availableDiscounts.put("Buy1Get50Off",new Buy1Get50Off());
        availableDiscounts.put("BuyBulkDiscount",new BuyBulkDiscount());
    }
    public static Map<Product,String> discountProductMap = new HashMap();
}
