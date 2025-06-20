package com.ecom.productservice.mapper;

import com.ecom.productservice.dto.ProductPurchaseResponse;
import com.ecom.productservice.dto.ProductRequest;
import com.ecom.productservice.dto.ProductResponse;
import com.ecom.productservice.model.Category;
import com.ecom.productservice.model.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest request)
        {
            return Product.builder()
                    .id(request.id())
                    .name(request.name())
                    .description(request.description())
                    .availableQuantity(request.availableQuantity())
                    .price(request.price())
                    .category(Category.builder().id(request.categoryId()).build())
                    .build();
        }


    public ProductResponse toProductResponse(Product product)
    {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()

        );
    }

    public ProductPurchaseResponse toproductPurchaseResponse(Product product,  double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
