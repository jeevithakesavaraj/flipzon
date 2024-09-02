package com.ideas2it.flipzon.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.exception.OutOfStock;
import com.ideas2it.flipzon.model.Product;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideas2it.flipzon.dao.StockDao;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.StockDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.mapper.StockMapper;
import com.ideas2it.flipzon.model.Stock;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public StockDto addStock(StockDto stockDto, Product product) {
        Stock stock = stockDao.saveAndFlush(StockMapper.convertDtoToEntity(stockDto));
        stock.setProduct(product);
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @Override
    public void deleteStock(Long id) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(id);
        if (stock.isDeleted()) {
            throw new ResourceNotFoundException("Stock", "Product ID : ", id);
        }
        stock.setDeleted(true);
        stockDao.saveAndFlush(stock);
    }

    @Override
    public List<StockDto> retrieveAllStock() {
        return stockDao.findByIsDeletedFalse().stream()
                .map(StockMapper::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StockDto updateStock(StockDto stockDto) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(stockDto.getId());
        if (null == stock) {
            throw new ResourceNotFoundException("Stock", "productId", stockDto.getId());
        }
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @Override
    public StockDto updateNewStock(StockDto stockDto) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(stockDto.getProductId());
        if (null == stock) {
            throw new ResourceNotFoundException("Stock", "productId", stockDto.getId());
        }
        stock.setInitialQuantity(stockDto.getInitialQuantity() + stock.getInitialQuantity());
        stock.setCurrentQuantity(stock.getCurrentQuantity() + stockDto.getInitialQuantity());
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @SneakyThrows
    @Override
    public StockDto retrieveStockByProductId(Long id) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(id);
        if (null == stock) {
            throw new ResourceNotFoundException("Stock", "Stock ID", id);
        } else if (stock.getCurrentQuantity() == 0) {
            throw new OutOfStock("Out of Stock");
        }
        return StockMapper.convertEntityToDto(stock);
    }

    @SneakyThrows
    @Override
    public void reduceStockByOrder(Long productId, int quantity) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(productId);
        if ((stock.getCurrentQuantity() - quantity) >= 0) {
            stock.setCurrentQuantity(stock.getCurrentQuantity() - quantity);
        } else {
            throw new OutOfStock("Out of stock");
        }
        stockDao.saveAndFlush(stock);
    }

    @Override
    public void updateStockByCancelledOrder(Long productId, int quantity) {
        Stock stock = stockDao.findByProductIdAndIsDeletedFalse(productId);
        stock.setCurrentQuantity(stock.getCurrentQuantity() + quantity);
        stockDao.saveAndFlush(stock);
    }
}
