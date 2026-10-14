package com.example.warehouse.inventory;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service 
@Transactional (readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional (readOnly = false)
    public ProductResponse createProduct(CreateProductRequest request)
    {
        if (productRepository.existsBySku(request.sku()))
            throw new IllegalArgumentException("Produkt mit SKU " + request.sku() + " existiert bereits!");

        Product product = new Product(request.sku(), request.name(), request.price(), request.stock());
        Product saved = productRepository.save(product);

        return this.toProductResponse(saved);
    }

    public ProductResponse getProductBySku(String sku)
    {
        return this.toProductResponse(this.getBySku(sku));
    }

    @Transactional (readOnly = false)
    public ProductResponse reduceStock(String sku, int quantity)
    {
        Product product = this.getBySku(sku);
        product.reduceStock(quantity);

        return this.toProductResponse(product);
    }

    private Product getBySku(String sku)
    {
        return productRepository.findBySku(sku).orElseThrow(() -> new NoSuchElementException("Produkt mit SKU " + sku + " existiert nicht!"));
    }

    private ProductResponse toProductResponse(Product product)
    {
        return new ProductResponse(product.getId(), product.getSku(), product.getName(), product.getPrice(), product.getStock());
    }
}
