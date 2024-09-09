package com.ideas2it.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import com.ideas2it.flipzon.dto.StockDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ideas2it.flipzon.dao.StockDao;
import com.ideas2it.flipzon.exception.OutOfStock;
import com.ideas2it.flipzon.model.Brand;
import com.ideas2it.flipzon.model.Category;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Stock;
import com.ideas2it.flipzon.model.Subcategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    private StockDao stockDao;

    @InjectMocks
    private StockServiceImpl stockService;

    private Brand brand;
    private Category category;
    private Subcategory subcategory;
    private Product product;
    private Stock stock;
    private StockDto stockDto;

    @BeforeEach
    void setup() {
        product = Product.builder()
                .id(1L)
                .name("iphone")
                .price(70000)
                .brand(brand)
                .category(category)
                .subcategory(subcategory)
                .stock(stock)
                .build();
        stock = Stock.builder()
                .id(1L)
                .initialQuantity(10)
                .currentQuantity(10)
                .product(product)
                .build();
        stockDto = StockDto.builder()
                .id(1L)
                .initialQuantity(10)
                .currentQuantity(10)
                .productId(product.getId())
                .build();
    }

    @Test
    void testDeletedStock() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(null);
        assertThrows(RuntimeException.class, ()-> {
           stockService.deleteStock(product.getId());
        });
    }
    @Test
    void testDeleteStock() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(stock);
        boolean flag = stockService.deleteStock(product.getId());
        assertTrue(flag);
    }

    @Test
    void testGetAllStock_Empty() {
        List<Stock> stocks = new ArrayList<>();
        when(stockDao.findByIsDeletedFalse()).thenReturn(stocks);
        assertThrows(RuntimeException.class, ()-> {
            stockService.retrieveAllStock();
        });
    }
    @Test
    void testGetAllStock() {
        when(stockDao.findByIsDeletedFalse()).thenReturn(List.of(stock));
        List<StockDto> response = stockService.retrieveAllStock();
        assertNotNull(response);
    }

    @Test
    void testUpdateStock_Empty() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(null);
        assertThrows(RuntimeException.class, ()-> {
            stockService.updateStock(1L, stockDto);
        });
    }

    @Test
    void testUpdateStock() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(stock);
        stock.setInitialQuantity(100);
        when(stockDao.saveAndFlush(any(Stock.class))).thenReturn(stock);
        StockDto response = stockService.updateStock(1L, stockDto);
        assertEquals(stock.getInitialQuantity(), response.getInitialQuantity());
    }

    @Test
    void testUpdateNewStock() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(stock);
        stock.setInitialQuantity(100);
        when(stockDao.saveAndFlush(any(Stock.class))).thenReturn(stock);
        StockDto response = stockService.updateNewStock(1L, stockDto);
        assertEquals(stock.getInitialQuantity(), response.getInitialQuantity());
    }

    @Test
    void testGetStockByproductIdEmpty() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(null);
        assertThrows(RuntimeException.class, ()-> {
            stockService.retrieveStockByProductId(product.getId());
        });
    }

    @Test
    void testGetStockByproductIdOutOfStock() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(stock);
        stock.setCurrentQuantity(0);
        assertThrows(OutOfStock.class, ()-> {
            stockService.retrieveStockByProductId(product.getId());
        });
    }

    @Test
    void testGetStockByproductId() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(stock);
        StockDto response = stockService.retrieveStockByProductId(product.getId());
        assertEquals(stock.getId(), response.getId());
    }

    @Test
    void testReduceStockByOrderOutOfStock() {
        when(stockDao.findByProductIdAndIsDeletedFalse(product.getId())).thenReturn(stock);
        stock.setCurrentQuantity(0);
        assertThrows(OutOfStock.class, ()-> {
            stockService.reduceStockByOrder(product.getId(), 10);
        });
    }
}
