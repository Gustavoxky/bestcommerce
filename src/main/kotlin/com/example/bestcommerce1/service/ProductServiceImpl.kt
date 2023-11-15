package com.example.bestcommerce1.service

import com.example.bestcommerce1.model.Product
import com.example.bestcommerce1.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    override fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    override fun getProductById(id: Long): Product? {
        return productRepository.findById(id).orElse(null)
    }
    @Transactional
    override fun createProduct(product: Product): Product {
        return productRepository.save(product)
    }

    override fun updateProduct(id: Long, product: Product): Product? {
        if (productRepository.existsById(id)) {
            return productRepository.save(product)
        }
        return null
    }

    override fun deleteProduct(id: Long) {
        productRepository.deleteById(id)
    }
}
