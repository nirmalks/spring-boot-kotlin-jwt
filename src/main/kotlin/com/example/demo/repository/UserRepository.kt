package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by nirmal on 15/6/18.
 */
@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername (username: String) : User
}