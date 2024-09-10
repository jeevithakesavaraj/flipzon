package com.ideas2it.flipzon.controller;

import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ideas2it.flipzon.dto.StockDto;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.service.ProductService;
import com.ideas2it.flipzon.service.StockService;

/**
 * <p>
 * StockController handles all incoming HTTP requests related to stocks and return the responses
 * </p>
 *
 * @author Gokul
 */
@RestController
@RequestMapping("flipzon/api/v1/admin/stocks")
public class StockController {

    @Autowired
    private StockService stockService;
    @Autowired
    private ProductService productService;

    /**
     * Add the Stock details by product ID based on the Admin request
     *
     * @param stockDto {@link StockDto}
     * @return StockDto with Http status Created.
     */
    @PostMapping("/products/{productId}")
    public ResponseEntity<StockDto> addStock(@PathVariable Long productId, @Valid @RequestBody StockDto stockDto) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(stockService.addStock(stockDto, product), HttpStatus.CREATED);
    }

    /**
     * Delete the Stock based on the Admin request
     *
     * @param id : id
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<StockDto> deleteStock(@PathVariable long id) {
        productService.retrieveProductById(id);
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get the all stocks form database
     *
     * @return List<StockDto> : list of stockDto
     */
    @GetMapping
    public ResponseEntity<List<StockDto>> getAllStocks() {
        return new ResponseEntity<>(stockService.retrieveAllStock(), HttpStatus.OK);
    }

    /**
     * update the Stock details by product ID based on the Admin request
     *
     * @param stockDto {@link StockDto}
     * @return StockDto with Http status Created.
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<StockDto> updateStock(@PathVariable Long productId, @Valid @RequestBody StockDto stockDto) {
        productService.retrieveProductById(productId);
        return new ResponseEntity<>(stockService.updateStock(productId, stockDto), HttpStatus.OK);
    }

    /**
     * Refill the Stock by product ID based on the Admin request
     *
     * @param stockDto {@link StockDto}
     * @return StockDto with Http status Created.
     */
    @PatchMapping("/products/{productId}")
    public ResponseEntity<StockDto> updateNewStock(@PathVariable Long productId, @Valid @RequestBody StockDto stockDto) {
        productService.retrieveProductById(productId);
        return new ResponseEntity<>(stockService.updateNewStock(productId, stockDto), HttpStatus.OK);
    }

    /**
     * Get the Stock based on the Admin request
     *
     * @param id : id of the stock
     * @return StockDto with Http status Created.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<StockDto> getStockByProductId(@PathVariable long id) {
        productService.retrieveProductById(id);
        return new ResponseEntity<>(stockService.retrieveStockByProductId(id), HttpStatus.OK);
    }
}
