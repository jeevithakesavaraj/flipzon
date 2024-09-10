package com.ideas2it.flipzon.service;

import java.util.List;

import com.ideas2it.flipzon.exception.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.StockDao;
import com.ideas2it.flipzon.dto.StockDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.exception.OutOfStock;
import com.ideas2it.flipzon.mapper.StockMapper;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Stock;

@Service
public class StockServiceImpl implements StockService {

    private static final Logger LOGGER = LogManager.getLogger(StockServiceImpl.class);

    @Autowired
    private StockDao stockDao;

    @Override
    public StockDto addStock(StockDto stockDto, Product product) {
        Stock stock = stockDao.findByProductId(product.getId());
        if (stock != null && !stock.isDeleted()) {
            LOGGER.warn("stock already added for this product name {}", product.getName());
            throw new MyException("stock already added");
        } else if (stock != null && stock.isDeleted()) {
            stock.setDeleted(false);
            stock.setInitialQuantity(stockDto.getInitialQuantity());
            stock.setCurrentQuantity(stockDto.getInitialQuantity());
            LOGGER.info("Stock added successfully");
            return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
        }
        stock = new Stock();
        stock.setProduct(product);
        stock.setInitialQuantity(stockDto.getInitialQuantity());
        stock.setCurrentQuantity(stockDto.getInitialQuantity());
        LOGGER.info("Stock added successfully");
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @Override
    public boolean deleteStock(Long id) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(id);
        if (stock.isDeleted()) {
            LOGGER.warn("Stock is not found in this product id {}", id);
            throw new ResourceNotFoundException("Stock", "Product ID : ", id);
        }
        stock.setDeleted(true);
        stockDao.saveAndFlush(stock);
        LOGGER.info("Stocks deleted Successfully");
        return true;
    }

    @Override
    public List<StockDto> retrieveAllStock() {
        List<StockDto> stockDtos = stockDao.findByIsDeletedFalse().stream()
                .map(StockMapper::convertEntityToDto).toList();
        if (stockDtos.isEmpty()) {
            LOGGER.warn("Stocks not found");
            throw new ResourceNotFoundException("stocks", "");
        }
        LOGGER.info("Get all stocks in DataBase");
        return stockDtos;
    }

    @Override
    public StockDto updateStock(Long productId, StockDto stockDto) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(productId);
        if (null == stock) {
            LOGGER.warn("Stock not found in this product id {}", productId);
            throw new ResourceNotFoundException("Stock", "productId", stockDto.getId());
        }
        LOGGER.info("Get stocks by the product id {}", productId);
        stock.setInitialQuantity(stockDto.getInitialQuantity());
        stock.setCurrentQuantity(stockDto.getInitialQuantity());
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @Override
    public StockDto updateNewStock(Long productId, StockDto stockDto) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(productId);
        if (null == stock) {
            LOGGER.warn("Stock not found in this product id {}", productId);
            throw new ResourceNotFoundException("Stock", "productId", stockDto.getId());
        }
        stock.setInitialQuantity(stockDto.getInitialQuantity() + stock.getInitialQuantity());
        stock.setCurrentQuantity(stock.getCurrentQuantity() + stockDto.getInitialQuantity());
        LOGGER.info("New stocks updated in this Product id {} ", productId);
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @Override
    public StockDto retrieveStockByProductId(Long id) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(id);
        if (null == stock) {
            LOGGER.warn("Stock not found in this product id {}", id);
            throw new OutOfStock("Out of Stock");
        } else if (stock.getCurrentQuantity() == 0) {
            LOGGER.warn("Out of Stock need to be refill this product id {}", id);
            throw new OutOfStock("Out of Stock");
        }
        LOGGER.info("Get Stock by product id {}", id);
        return StockMapper.convertEntityToDto(stock);
    }

    @Override
    public void reduceStockByOrder(Long productId, int quantity) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(productId);
        if ((stock.getCurrentQuantity() - quantity) >= 0) {
            LOGGER.info("Reduce stock quantity ");
            stock.setCurrentQuantity(stock.getCurrentQuantity() - quantity);
        } else {
            LOGGER.warn("Out of Stock need to be refill this product id {}", productId);
            throw new OutOfStock("Out of stock");
        }
        stockDao.saveAndFlush(stock);
    }

    @Override
    public void updateStockByCancelledOrder(Long productId, int quantity) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(productId);
        stock.setCurrentQuantity(stock.getCurrentQuantity() + quantity);
        stockDao.saveAndFlush(stock);
        LOGGER.info("Refill the canceled ordered stocks in this product id {}", productId);
    }
}
