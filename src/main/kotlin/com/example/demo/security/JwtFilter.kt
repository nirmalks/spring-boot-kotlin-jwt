package com.example.demo.security

/**
 * Created by nirmal on 17/6/18.
 */

import com.example.demo.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter constructor(@Autowired authenticationManager: AuthenticationManager): UsernamePasswordAuthenticationFilter() {
    val EXPIRATION_TIME : Long = 864_000_000
    val SECRET: String = "Nirmal"
    val HEADER_STRING: String = "Authorization"
    val TOKEN_PREFIX: String = "Bearer "

    @Override
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
        try {
            val creds : User  = ObjectMapper().readValue(req.getInputStream() , User::class.java)
            return authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(creds.username , creds.password , ArrayList())
            )
        } catch(ex : IOException) {
            throw RuntimeException(ex)
        }
    }

    @Override
    override fun successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse , chain: FilterChain ,
                                          auth: Authentication) {
        val token : String = Jwts.builder()
                .setSubject(( auth.principal as User).username)
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512 , SECRET.toByteArray())
                .compact()
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
    }
}

