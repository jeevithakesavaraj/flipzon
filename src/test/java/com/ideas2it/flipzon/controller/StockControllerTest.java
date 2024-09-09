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
import static org.mockito.ArgumentMatchers.anyLong;
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
                .initialQuantity(10)
                .currentQuantity(5)
                .build();
    }

    @Test
    void testAddStock() {
        when(stockService.addStock(stockDto, product)).thenReturn(stockDto);
        when(productService.getProductById(anyLong())).thenReturn(product);
        ResponseEntity<StockDto> response = stockController.addStock(product.getId(), stockDto);
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
        when(stockService.updateStock(anyLong(), stockDto)).thenReturn(stockDto);
        ResponseEntity<StockDto> response = stockController.updateStock(anyLong(), stockDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stockDto, response.getBody());
        verify(stockService, times(1)).updateStock(1L, stockDto);
    }

    @Test
    void testRetrieveStockByProductId() {
        when(stockService.retrieveStockByProductId(anyLong())).thenReturn(stockDto);
        ResponseEntity<StockDto> response = stockController.getStockByProductId(anyLong());
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
        when(stockService.updateNewStock(anyLong(), stockDto)).thenReturn(stockDto);
        ResponseEntity<StockDto> response = stockController.updateNewStock(anyLong(), stockDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(stockService, times(1)).updateNewStock(1L, stockDto);
    }
}
