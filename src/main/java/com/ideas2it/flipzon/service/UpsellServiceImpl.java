package com.ideas2it.flipzon.service;

import com.ideas2it.flipzon.dto.UpsellResponseDto;
import com.ideas2it.flipzon.dao.UpsellDao;
import com.ideas2it.flipzon.dto.UpsellDto;
import com.ideas2it.flipzon.exception.ResourceNotFoundException;
import com.ideas2it.flipzon.mapper.ProductMapper;
import com.ideas2it.flipzon.model.Product;
import com.ideas2it.flipzon.model.Upsell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UpsellServiceImpl implements UpsellService {
    private static final Logger LOGGER = LogManager.getLogger(UpsellServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UpsellDao upsellDao;

    @Override
    public UpsellResponseDto addUpsell(UpsellDto upsellDto) {
        Product product = productService.getProductById(upsellDto.getProductId());
        Product upsellProduct = productService.getProductById(upsellDto.getUpsellProductId());
        Upsell upsell = upsellDao.findByProductId(upsellDto.getProductId());
        if (null == upsell) {
            upsell = new Upsell();
            upsell.setProduct(product);
            upsell.setProducts(Set.of(upsellProduct));
        }else {
        if (upsell.getProducts().isEmpty()) {
            upsell.setProducts(Set.of(upsellProduct));
        } else {
            var products = upsell.getProducts();
            products.add(upsellProduct);
            upsell.setProducts(products);
        }}
        upsell = upsellDao.saveAndFlush(upsell);
        return UpsellResponseDto.builder()
                .productName(product.getName())
                .price(product.getPrice())
                .productDtos(upsell.getProducts().stream().map(ProductMapper::convertEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public UpsellResponseDto deleteUpsell(UpsellDto upsellDto) {
        productService.getProductById(upsellDto.getProductId());
        Product upsellProduct = productService.getProductById(upsellDto.getUpsellProductId());
        Upsell upsell = upsellDao.findByProductId(upsellDto.getProductId());
        if (upsell == null) {
            LOGGER.warn("No Cross-sell products available for this product");
            throw new ResourceNotFoundException();
        }
        if (upsell.getProducts().isEmpty()) {
            LOGGER.warn("Cross-sell products not available for this product");
            throw new ResourceNotFoundException();
        } else {
            upsell.getProducts().remove(upsellProduct);
        }
        upsellDao.saveAndFlush(upsell);
        return UpsellResponseDto.builder()
                .productName(upsellProduct.getName())
                .price(upsellProduct.getPrice())
                .productDtos(upsell.getProducts().stream()
                        .map(ProductMapper::convertEntityToDto)
                        .collect(Collectors.toSet()))
                .build();
    }
}
