package com.ideas2it.flipzon.controller;

import java.util.List;

import com.ideas2it.flipzon.dto.StockDto;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.service.ProductService;
import com.ideas2it.flipzon.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {
    @Mock
    private StockService stockService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private StockController stockController;

    private StockDto stockDto;

    private Product product;

    @BeforeEach
    void setUp() {
         product = Product.builder()
                .id(1L)
                .name("iphone12")
                .price(80000)
                .build();
        stockDto = StockDto.builder()
                .id(1L)
                .productId(product.getId())
                .initialQuantity(10)
                .currentQuantity(5)
                .build();
    }

    @Test
    void testAddStock() {
        when(stockService.addStock(stockDto, product)).thenReturn(stockDto);
        when(productService.getProductById(stockDto.getProductId())).thenReturn(product);
        ResponseEntity<StockDto> response = stockController.addStock(stockDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(stockDto, response.getBody());
        verify(stockService, times(1)).addStock(stockDto, product);
    }

    @Test
    void testDeleteStock() {
        when(stockService.deleteStock(stockDto.getId())).thenReturn(true);
        ResponseEntity<StockDto> response = stockController.deleteStock(stockDto.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(stockService, times(1)).deleteStock(stockDto.getId());
    }

    @Test
    void testUpdateStock() {
        when(stockService.updateStock(stockDto)).thenReturn(stockDto);
        ResponseEntity<StockDto> response = stockController.updateStock(stockDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stockDto, response.getBody());
        verify(stockService, times(1)).updateStock(stockDto);
    }

    @Test
    void testRetrieveStockByProductId() {
        when(stockService.retrieveStockByProductId(stockDto.getProductId())).thenReturn(stockDto);
        ResponseEntity<StockDto> response = stockController.getStockByProductId(stockDto.getProductId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stockDto, response.getBody());
        verify(stockService, times(1)).retrieveStockByProductId(stockDto.getId());
    }

    @Test
    void testRetrieveStocks() {
        when(stockService.retrieveAllStock()).thenReturn(List.of(stockDto));
        ResponseEntity<List<StockDto>> response = stockController.getAllStocks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(stockService, times(1)).retrieveAllStock();
    }

    @Test
    void TestUpdateNewStock() {
        when(stockService.updateNewStock(stockDto)).thenReturn(stockDto);
        ResponseEntity<StockDto> response = stockController.updateNewStock(stockDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(stockService, times(1)).updateNewStock(stockDto);
    }
}
