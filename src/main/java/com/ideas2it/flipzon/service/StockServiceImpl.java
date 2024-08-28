package com.ideas2it.flipzon.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ideas2it.flipzon.dao.StockDao;
import com.ideas2it.flipzon.dto.ProductDto;
import com.ideas2it.flipzon.dto.StockDto;
import com.ideas2it.flipzon.exception.MyException;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.mapper.StockMapper;
import com.ideas2it.flipzon.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;
    @Autowired
    private ProductService productService;

    @Override
    public StockDto addStock(StockDto stockDto) {
        ProductDto productDto = productService.retrieveProductById(stockDto.getProductId());
        Stock stock = stockDao.saveAndFlush(StockMapper.convertDtoToEntity(stockDto));
        stock.setProduct(ProductMapper.convertDtoToEntity(productDto));
        return StockMapper.convertEntityToDto(stockDao.saveAndFlush(stock));
    }

    @Override
    public void deleteStock(Long id) {
        Stock stock = stockDao.findByIdAndIsDeletedFalse(id);
        if (stock.isDeleted()) {
            throw new MyException("Stock Already Deleted : " + id);
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
        Stock stock = stockDao.findByIdAndIsDeletedFalse(stockDto.getId());
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

    @Override
    public StockDto retrieveStockById(Long id) {
        Stock stock = stockDao.findByIdAndIsDeletedFalse(id);
        if (null == stock) {
            throw new ResourceNotFoundException("Stock", "Stock ID", id);
        }
        return StockMapper.convertEntityToDto(stock);
    }
}
