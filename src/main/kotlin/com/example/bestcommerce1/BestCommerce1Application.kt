package com.example.bestcommerce1

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class BestCommerce1Application

fun main(args: Array<String>) {
	runApplication<BestCommerce1Application>(*args)
}
