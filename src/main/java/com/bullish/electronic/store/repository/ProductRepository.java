package com.bullish.electronic.store.repository;

import com.bullish.electronic.store.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

     final List<Product> products = new ArrayList<>();

    public Product addProduct(Product p){
        products.add(p);
        return p;
    }

    public void deleteProduct(Product p){
        products.remove(p);
    }

    public Product deleteProductById(Long id) throws Exception {
        Product p = findById(id);
        if(products.remove(p))
            return p;
        else
            throw new Exception("Product not Found");
        //products.removeIf(p -> id.equals(p.getId()))

    }
    public List<Product> getAllProducts() {
        return products;
    }

    public Product findById(Long productId) {
        return  products.stream()
                .filter(product -> productId.equals(product.getId()))
                .findFirst().get();
    }
}
