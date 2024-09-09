package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dao.CrossSellDao;
import com.ideas2it.flipzon.dto.CrossSellRequestDto;
import com.ideas2it.flipzon.dto.CrossSellResponseDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Crosssell;
import com.ideas2it.flipzon.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CrossSellServiceImpl implements CrossSellService {

    private static final Logger LOGGER = LogManager.getLogger(CrossSellServiceImpl.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private CrossSellDao crossSellDao;

    @Override
    public CrossSellResponseDto addCrossSellProduct(CrossSellRequestDto crossSellRequestDto) {
        Product mainProduct = productService.getProductById(crossSellRequestDto.getProductId());
        Product crossSellProduct = productService.getProductById(crossSellRequestDto.getCrossSellProductId());
        Crosssell crossSell = crossSellDao.findByProductId(crossSellRequestDto.getProductId());
        if (crossSell == null) {
            crossSell = new Crosssell();
            crossSell.setProduct(mainProduct);
            crossSell.setProducts(Set.of(crossSellProduct));
        } else {
            if (crossSell.getProducts().isEmpty()) {
                crossSell.setProducts(Set.of(crossSellProduct));
            } else {
                var products = crossSell.getProducts();
                products.add(crossSellProduct);
                crossSell.setProducts(products);
            }
        }
        crossSell = crossSellDao.saveAndFlush(crossSell);
        LOGGER.info("Cross-sell product added to this id : {}", crossSellRequestDto.getProductId());
        return CrossSellResponseDto.builder()
                .ProductName(crossSell.getProduct().getName())
                .price(crossSell.getProduct().getPrice())
                .productDtos(crossSell.getProducts().stream()
                        .map(ProductMapper::convertEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public CrossSellResponseDto removeCrossSellProduct(CrossSellRequestDto crossSellRequestDto) {
        productService.getProductById(crossSellRequestDto.getProductId());
        Product crossSellProduct = productService.getProductById(crossSellRequestDto.getCrossSellProductId());
        Crosssell crossSell = crossSellDao.findByProductId(crossSellRequestDto.getProductId());
        if (crossSell == null) {
            LOGGER.warn("No Cross-sell products available for this product");
            throw new ResourceNotFoundException();
        } else if (crossSell.getProducts().isEmpty()) {
            LOGGER.warn("Cross-sell products not available for this product");
            throw new ResourceNotFoundException();
        } else {
            crossSell.getProducts().remove(crossSellProduct);
        }
        crossSellDao.saveAndFlush(crossSell);
        return CrossSellResponseDto.builder()
                .ProductName(crossSellProduct.getName())
                .price(crossSellProduct.getPrice())
                .productDtos(crossSell.getProducts().stream()
                        .map(ProductMapper::convertEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CrossSellResponseDto getCrossSellProduct(long productId) {
        Crosssell crossSell = crossSellDao.findByProductId(productId);
        if (crossSell == null) {
            LOGGER.warn("Cross-sell products not available for this product Id {}", productId);
            throw new ResourceNotFoundException("Cross-sell products not available for this product Id {}", productId);
        }
        return CrossSellResponseDto.builder()
                .ProductName(crossSell.getProduct().getName())
                .price(crossSell.getProduct().getPrice())
                .productDtos(crossSell.getProducts().stream()
                        .map(ProductMapper::convertEntityToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
