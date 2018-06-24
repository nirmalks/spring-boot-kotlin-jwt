package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
class UserController(@Autowired private val userRepository: UserRepository ,@Autowired val bCryptPasswordEncoder : BCryptPasswordEncoder) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody user: User) {
        user.password = bCryptPasswordEncoder.encode(user.password)
        userRepository.save(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<User> {
        val cUser: User? = userRepository.findByUsername(user.username)
        if(cUser != null && bCryptPasswordEncoder.matches(user.password,cUser.password)) {
            return ResponseEntity<User>(user, HttpStatus.OK)
        }
        return ResponseEntity<User>(user, HttpStatus.OK)
    }
}