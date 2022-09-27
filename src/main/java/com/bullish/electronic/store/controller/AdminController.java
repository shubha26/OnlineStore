package com.bullish.electronic.store.controller;

import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.model.ProductDto;
import com.bullish.electronic.store.service.DiscountService;
import com.bullish.electronic.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
public class AdminController {

    @Autowired
    private ProductService productService;
    @Autowired
    private DiscountService discountService;

    //add a Product
    @PostMapping("/admin/products")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
        //Product product;
        try {
        Product p = productService.createProduct(productDto);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // delete a Product
    @DeleteMapping("/admin/products/{Id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long Id) {
        try {
            productService.removeProduct(Id);
        } catch (Exception e) {
         return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/discount/{discountName}/products/{productId}")
    public ResponseEntity<?> addDiscount(@PathVariable String discountName, @PathVariable Long productId) {

        try {
            String value = discountService.addDiscount(discountName, productId);
            return ResponseEntity.ok(value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
