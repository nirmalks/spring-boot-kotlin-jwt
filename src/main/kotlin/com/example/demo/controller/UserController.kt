package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by nirmal on 15/6/18.
 */
@RestController
@RequestMapping("/users")
public class UserController(private val userRepository: UserRepository , val bCryptPasswordEncoder : BCryptPasswordEncoder) {


    @PostMapping("/sign-up")
    fun signUp(@RequestBody user: User) {
        user.password = bCryptPasswordEncoder.encode(user.password)
        userRepository.save(user)
    }


}