package com.bullish.electronic.store;

import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.model.ReceiptDto;
import com.bullish.electronic.store.service.CartService;
import com.bullish.electronic.store.service.DiscountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = StoreApplication.class)
 class CartTests {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        HttpSession session;
        @MockBean
        CartService cartService;
        @MockBean
        DiscountService discountService;

        @Test
        public void addProductToCart() throws Exception {

             when(cartService.addToCart(Mockito.any(HttpSession.class),Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
            String uri = "/user/cart/1/2";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            assertEquals(200, mvcResult.getResponse().getStatus());
        }

        @Test
        void removeFromCart() throws Exception {
            when(cartService.removeFromCart(Mockito.any(HttpSession.class),Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
            String uri = "/user/cart/1/1";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            assertEquals(204, mvcResult.getResponse().getStatus());
        }
        @Test
        void removeFromCartException() throws Exception {
            when(cartService.removeFromCart(Mockito.any(HttpSession.class),Mockito.anyLong(), Mockito.anyInt())).thenThrow(NullPointerException.class);
            String uri = "/user/cart/1/1";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            assertEquals(404,mvcResult.getResponse().getStatus());
        }

        @Test
        void getReceiptWithoutDiscount() throws Exception {

           when(cartService.getReceipt(Mockito.any(HttpSession.class))).thenReturn(getReceiptDto("None"));
            String uri = "/user/cart/receipt";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();


            ReceiptDto receiptDto = jsonToReceipt(mvcResult.getResponse().getContentAsString());
            assertEquals(200, mvcResult.getResponse().getStatus());
            assertEquals( 2, receiptDto.getItemsPurchased().size());
            assertEquals(300, receiptDto.getTotalPrice());
            assertEquals(300, receiptDto.getFinalDiscountedPrice());
        }

    @Test
    void getReceiptWithDiscount() throws Exception {

        when(cartService.getReceipt(Mockito.any(HttpSession.class))).thenReturn(getReceiptDto("Buy1Get50Off"));
        String uri = "/user/cart/receipt";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();


        ReceiptDto receiptDto = jsonToReceipt(mvcResult.getResponse().getContentAsString());
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals( 2, receiptDto.getItemsPurchased().size());
        assertEquals(300, receiptDto.getTotalPrice());
        assertEquals(250, receiptDto.getFinalDiscountedPrice());
    }

    private ReceiptDto getReceiptDto(String discount) {
        ReceiptDto receiptDto;
        Product product1 = new Product("Table", 100.00, "Office Table", 100);
        Product product2 = new Product("HeadPhones", 20.00, "Meetings HeadSet", 500);
        Map<Product, AtomicInteger> itemsPurchased=new HashMap<>();
        itemsPurchased.put(product1, new AtomicInteger(2));
        itemsPurchased.put(product2,new AtomicInteger(5));
        if("Buy1Get50Off".equalsIgnoreCase(discount)) {
            receiptDto =  new ReceiptDto(300, 250, itemsPurchased);
        } else {
            receiptDto = new ReceiptDto(300, 300, itemsPurchased);
        }
        return receiptDto;
    }

    @Before
        protected String mapToJson(Object obj) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        }
    protected ReceiptDto jsonToReceipt(String obj) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return  objectMapper.readValue(obj,ReceiptDto.class);
    }

        @Test
        void contextLoads() {
        }

    }
    

