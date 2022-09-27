package com.bullish.electronic.store.controller;

import com.bullish.electronic.store.model.ReceiptDto;
import com.bullish.electronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Component
@RestController
public class CustomerController {

        @Autowired
        private CartService cartService;

        @GetMapping("/user/cart/receipt")
        public ResponseEntity<ReceiptDto> getReceipt(HttpSession session) {
            ReceiptDto receiptDto;
            try {
                 receiptDto = cartService.getReceipt(session);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(receiptDto);
        }


        //add a Product to the cart
        @PostMapping("/user/cart/{productId}/{quantity}")
        public ResponseEntity<?> addProduct(@PathVariable Long productId, @PathVariable(value = "quantity", required = false) int quantity,
                                            HttpSession session) {

            try {
                cartService.addToCart(session, productId, quantity);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().build();
        }

        @DeleteMapping("/user/cart/{productId}/{quantity}")
        public ResponseEntity<?> removeProduct(@PathVariable Long productId, @PathVariable(value = "quantity", required = false) int quantity,
                                               HttpSession session) {

            try {
                cartService.removeFromCart(session, productId, quantity);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.noContent().build();
        }

    }


