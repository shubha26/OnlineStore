package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.service.discount.Discount;
import com.bullish.electronic.store.service.discount.DiscountUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DiscountService {

    @Autowired
    DiscountUtility discountUtility;
    @Autowired
    ProductService productService;
    public String addDiscount(String discountName, Long productId) {
        return discountUtility.discountProductMap.put(productService.getProduct(productId),discountName);
    }

    public double getDiscountPrice(Map<Product, AtomicInteger> products) {
        Set<Product> productsInCart = products.keySet();
        double finalPrice = 0.0;
        for(Product product : productsInCart) {
            //Finding the discount and the
            Map<Product, String> discountProductMap = discountUtility.discountProductMap;
            if (discountProductMap.containsKey(product)) {
                String discountName = discountProductMap.get(product);
                Discount discount1 = discountUtility.availableDiscounts.get(discountName);
                discount1.setProduct(product);
                finalPrice += discount1.getDiscountPrice(products);
            }
            else
            finalPrice += product.getPrice()*products.get(product).get();
        }
        return finalPrice;
    }
}
