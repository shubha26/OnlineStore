package com.bullish.electronic.store.repository;

import com.bullish.electronic.store.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CartRepository {

    public void addProduct(Map<Product, AtomicInteger> products, Product product){
        products.computeIfAbsent(product,k->new AtomicInteger(0)).incrementAndGet();

    }
    public boolean addProductWithQuantity(Map<Product, AtomicInteger> products, Product product, int quantity) {
       return products.computeIfAbsent(product,k->new AtomicInteger(0)).addAndGet(quantity)==quantity;
    }

    public void removeProduct(Map<Product, AtomicInteger> products, Product product) {

        products.computeIfPresent(product,(k,v)->{
            int value=  v.decrementAndGet();
            if(value==0) return null;
            else return new AtomicInteger(value);
        });
    }
    @Transactional
    public boolean removeProductWithQuantity(Map<Product, AtomicInteger> products, Product product, int quantity) throws Exception {

        int result =  products.computeIfPresent(product,(k,v)->{
            int value= v.get()-quantity;
            if(value==0) return null;
            else return new AtomicInteger(value);
        }).get();
        if(result==quantity)
            return true;
        else throw new Exception("Cart : Error in removing the Product");
    }
}
