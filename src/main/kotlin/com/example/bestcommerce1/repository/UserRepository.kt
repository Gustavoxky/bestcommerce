package com.example.bestcommerce1.repository

import com.example.bestcommerce1.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Int> {
    fun findByEmail(email:String): User?
}
