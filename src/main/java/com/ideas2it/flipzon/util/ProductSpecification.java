package com.ideas2it.flipzon.util;

import com.ideas2it.flipzon.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasProductCategoryName(String productCategoryName) {
        return (root, query, criteriaBuilder) ->
                productCategoryName == null ? null : criteriaBuilder.like(root.get("category").get("name"), "%" + productCategoryName + "%");
    }
    public static Specification<Product> hasProductSubcategoryName(String productSubcategoryName) {
        return (root, query, criteriaBuilder) ->
                productSubcategoryName == null ? null : criteriaBuilder.like(root.get("subcategory").get("name"), "%" + productSubcategoryName + "%");
    }
    public static Specification<Product> hasProductBrandName(String productBrandName) {
        return (root, query, criteriaBuilder) ->
                productBrandName == null ? null : criteriaBuilder.like(root.get("brand").get("name"), "%" + productBrandName + "%");
    }
}
