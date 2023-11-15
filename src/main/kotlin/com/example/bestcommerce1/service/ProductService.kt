package com.example.bestcommerce1.service

import com.example.bestcommerce1.model.Product

interface ProductService {
    fun getAllProducts(): List<Product>
    fun getProductById(id: Long): Product?
    fun createProduct(product: Product): Product
    fun updateProduct(id: Long, product: Product): Product?
    fun deleteProduct(id: Long)
}


