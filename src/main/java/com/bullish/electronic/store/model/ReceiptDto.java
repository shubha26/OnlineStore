package com.bullish.electronic.store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDto {

    private double totalPrice;
    private double finalDiscountedPrice;
    @JsonDeserialize(keyUsing = MyDeserializer.class)
    Map<Product, AtomicInteger> itemsPurchased;

    static class MyDeserializer  extends KeyDeserializer {
        @Override
        public String deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            //Use the string key here to return a real map key object
            return key;
            //return new Product(key,1.0,"test",100);
        }
    }
}
