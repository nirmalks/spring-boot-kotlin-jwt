package com.example.demo.service

/**
 * Created by nirmal on 17/6/18.
 */
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(@Autowired val userRepository: UserRepository): UserDetailsService {
    @Override
    override fun loadUserByUsername(username : String) : UserDetails {
        val user: User = userRepository.findByUsername(username)
        if(user == null) {
            throw UsernameNotFoundException(username)
        }
        return org.springframework.security.core.userdetails.User(user.username,user.password, emptyList())
    }
}