package com.bullish.electronic.store;

import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.model.ProductDto;
import com.bullish.electronic.store.service.DiscountService;
import com.bullish.electronic.store.service.ProductService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = StoreApplication.class)
class ProductTests {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        ProductService productService;
        @MockBean
        DiscountService discountService;

        @Autowired
        HttpSession session;

        @Test
        public void addProduct() throws Exception {

            Product product = new Product("HeadPhones", 20.00, "Meetings HeadSet", 500);
            when(productService.createProduct(Mockito.any(ProductDto.class))).thenReturn(product);
            String uri = "/admin/products";
            ProductDto productDto = new ProductDto("HeadPhones", 20.00, "Meetings HeadSet", 500);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).content(mapToJson(productDto))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertTrue( mvcResult.getResponse().getContentAsString().contains("HeadPhones"));
        }

        @Test
        void removeProduct() throws Exception {
            Product product = new Product("HeadPhones", 20.00, "Meetings HeadSet", 500);
            when(productService.removeProduct(Mockito.anyLong())).thenReturn(product);
            String uri = "/admin/products/1";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            assertEquals(204, status);
        }
        @Test
    void removeProductException() throws Exception {
        when(productService.removeProduct(Mockito.anyLong())).thenThrow(NullPointerException.class);
        String uri = "/admin/products/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(404,mvcResult.getResponse().getStatus());
    }

    @Test
    void addDiscount() throws Exception {
        when(discountService.addDiscount(Mockito.anyString(),Mockito.anyLong())).thenReturn("Buy1Get50Off");
                String uri = "/admin/discount/Buy1Get50Off/products/1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200,mvcResult.getResponse().getStatus());
        assertTrue( mvcResult.getResponse().getContentAsString().contains("Buy1Get50Off"));
    }
        @Before
        protected String mapToJson(Object obj) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        }


        @Test
        void contextLoads() {
        }

    }

