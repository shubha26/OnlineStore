package com.bullish.electronic.store.service.discount;

import com.bullish.electronic.store.model.Product;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
public class Buy1Get50Off implements Discount{

    //For now this is applicable for 1 product, this should be a list of products
     private Product product;

    public double getDiscountPrice(Map<Product, AtomicInteger> cart) {

         int numberOfProducts = cart.get(product).get();
        if (numberOfProducts==0) {
            return 0.0;
        }
        //one item on full price and second on 50% off So mathematically the total
        // would be 3/4 of the total price.
        if(numberOfProducts %2 ==0 ) {
            return numberOfProducts * product.getPrice() * 3 / 4;
        }
        else{
            int remainingProducts =  numberOfProducts%2;
            int num = numberOfProducts-remainingProducts;
            return (num * product.getPrice() * 3 / 4) + remainingProducts*product.getPrice();
            }
    }
}
