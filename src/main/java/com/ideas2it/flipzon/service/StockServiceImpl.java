package com.ideas2it.flipzon.service;

import java.util.List;

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
        Stock stock = stockDao.saveAndFlush(StockMapper.convertDtoToEntity(stockDto));
        stock.setProduct(product);
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
    public StockDto updateStock(StockDto stockDto) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(stockDto.getProductId());
        if (null == stock) {
            LOGGER.warn("Stock not found in this product id {}", stockDto.getProductId());
            throw new ResourceNotFoundException("Stock", "productId", stockDto.getId());
        }
        LOGGER.info("Get stocks in thi product id {}", stockDto.getProductId());
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @Override
    public StockDto updateNewStock(StockDto stockDto) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(stockDto.getProductId());
        if (null == stock) {
            LOGGER.warn("Stock not found in this product id {}", stockDto.getProductId());
            throw new ResourceNotFoundException("Stock", "productId", stockDto.getId());
        }
        stock.setInitialQuantity(stockDto.getInitialQuantity() + stock.getInitialQuantity());
        stock.setCurrentQuantity(stock.getCurrentQuantity() + stockDto.getInitialQuantity());
        LOGGER.info("New stocks updated in this Product id {} ", stockDto.getProductId());
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @Override
    public StockDto retrieveStockByProductId(Long id) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(id);
        if (null == stock) {
            LOGGER.warn("Stock not found in this product id {}", id);
            throw new ResourceNotFoundException("Stock", "Stock ID", id);
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
