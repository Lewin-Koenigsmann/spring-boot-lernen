package com.example.warehouse.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping 
    public ResponseEntity<ProductResponse> createProduct (@Valid @RequestBody CreateProductRequest request)
    {
        ProductResponse response = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping ("/{sku}")
    public ProductResponse getProductBySku(@PathVariable String sku)
    {
        return productService.getProductBySku(sku);
    }

    @PatchMapping ("/{sku}/reduce-stock")
    public ProductResponse reduceStock(@PathVariable String sku, @RequestParam int quantity)
    {
        return productService.reduceStock(sku, quantity);
    }
}
