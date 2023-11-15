package com.example.bestcommerce1.controller

import com.example.bestcommerce1.model.Product
import com.example.bestcommerce1.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): List<Product> = productService.getAllProducts()

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): Product? = productService.getProductById(id)

//    @GetMapping("/login")
//    fun getLogin(@PathVariable id: Long): Product? = productService.getProductById(id)

    @PostMapping
    fun createProduct(@RequestBody product: Product): Product = productService.createProduct(product)

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): Product? =
            productService.updateProduct(id, product)

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long) = productService.deleteProduct(id)
}
