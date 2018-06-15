package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by nirmal on 15/6/18.
 */
public interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername (username: String) : User
}