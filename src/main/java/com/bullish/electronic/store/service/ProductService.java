package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.ProductDto;
import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductDto productDto) {
        Product newProduct = new Product(productDto.getName(),productDto.getPrice(),productDto.getInfo(),productDto.getStockQuantity());
        return productRepository.addProduct(newProduct);
    }

    public Product removeProduct(Long id) throws Exception {
        return productRepository.deleteProductById(id);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId);
    }
}
