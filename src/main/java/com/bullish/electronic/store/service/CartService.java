package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.Cart;
import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.model.ReceiptDto;
import com.bullish.electronic.store.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    DiscountService discountService;

    public Cart getCart(HttpSession session){
        if(session.getAttribute("cart")== null){
        return new Cart();
        }
        return (Cart)session.getAttribute("cart");
    }

    public boolean addToCart(HttpSession session, Long productId, int quantity){
        Cart cart = getCart(session);
        Map<Product, AtomicInteger> products = cart.getProducts();
        Product product = productService.getProduct(productId);
        boolean result = cartRepository.addProductWithQuantity(products,product,quantity);
        setCart(session,cart);
        return result;
    }

    private void setCart(HttpSession session, Cart cart) {
        session.setAttribute("cart",cart);
    }

    public boolean removeFromCart(HttpSession session, Long productId, int quantity) throws Exception {
        Cart cart = getCart(session);
         Product product = productService.getProduct(productId);

        boolean result =  cartRepository.removeProductWithQuantity(cart.getProducts(),product,quantity);
        setCart(session,cart);
        return result;
    }

    public double getFinalTotalPrice(HttpSession session) {
        Cart cart = getCart(session);
      return discountService.getDiscountPrice(cart.getProducts());

    }
    public double getTotalPrice(HttpSession session) {
        Cart cart = getCart(session);
        double amount = 0.0;
        Map<Product, AtomicInteger> products=  cart.getProducts();
        for(Product p : products.keySet()){
            amount += p.getPrice()*products.get(p).get();
        }
        return amount;

    }

    public ReceiptDto getReceipt(HttpSession session) {
        Cart cart = getCart(session);
        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setTotalPrice(getTotalPrice(session));
        receiptDto.setFinalDiscountedPrice(getFinalTotalPrice(session));
        receiptDto.setItemsPurchased(cart.getProducts());
        return receiptDto;
    }
}
