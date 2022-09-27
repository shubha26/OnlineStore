package com.bullish.electronic.store.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @NonNull
    @EqualsAndHashCode.Include
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String info;
    private int stockQuantity;
    @CreationTimestamp
    private LocalDateTime createdDateTime;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;
    static Long nextId = 1L;
    public Product(String name, Double price, String info, int stockQuantity) {
        this.id = getNextId();
        this.name = name;
        this.price = price;
        this.info = info;
        this.stockQuantity = stockQuantity;
        this.createdDateTime = LocalDateTime.now();
        this.lastUpdatedTime = LocalDateTime.now();
    }
    static Long getNextId(){
        return nextId++;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", info='" + info + '\'' +
                '}';
    }
}
