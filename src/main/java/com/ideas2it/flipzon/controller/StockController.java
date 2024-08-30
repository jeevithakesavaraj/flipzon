package com.ideas2it.flipzon.controller;

import java.util.List;

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

    /**
     * Create the Stock based on the Admin request
     *
     * @param stockDto {@link StockDto}
     * @return StockDto with Http status Created.
     */
    @PostMapping
    public ResponseEntity<StockDto> addStock(@RequestBody StockDto stockDto) {
        return new ResponseEntity<>(stockService.addStock(stockDto), HttpStatus.CREATED);
    }

    /**
     * Delete the Stock based on the Admin request
     *
     * @param id : id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<StockDto> deleteStock(@PathVariable long id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
    @PutMapping
    public ResponseEntity<StockDto> updateStock(@RequestBody StockDto stockDto) {
        return new ResponseEntity<>(stockService.updateStock(stockDto), HttpStatus.OK);
    }
    /**
     * Refill the Stock by product ID based on the Admin request
     *
     * @param stockDto {@link StockDto}
     * @return StockDto with Http status Created.
     */
    @PatchMapping
    public ResponseEntity<StockDto> updateNewStock(@RequestBody StockDto stockDto) {
        return new ResponseEntity<>(stockService.updateNewStock(stockDto), HttpStatus.OK);
    }

    /**
     * update the Stock based on the Admin request
     *
     * @param id : id of the stock
     * @return StockDto with Http status Created.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockDto> getStockById(@PathVariable long id) {
        return new ResponseEntity<>(stockService.retrieveStockById(id), HttpStatus.OK);
    }
}
