package com.example.demo.security


/**
 * Created by nirmal on 17/6/18.
 */

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthFilter(@Autowired authManager: AuthenticationManager): BasicAuthenticationFilter(authManager) {
    val SECRET: String = "Nirmal"
    val HEADER_STRING: String = "Authorization"
    val TOKEN_PREFIX: String = "Bearer "
    @Override
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain):Unit {
        val header: String? = request.getHeader(HEADER_STRING)
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        val authentication: UsernamePasswordAuthenticationToken? = getAuthentication(request)
        SecurityContextHolder.getContext().setAuthentication(authentication)
        chain.doFilter(request,response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token: String? = request?.getHeader(HEADER_STRING)
        if(token != null) {
            val user: String? = Jwts.parser()
                    .setSigningKey(SECRET.toByteArray())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                    .body
                    .subject
            if(user !== null) {
                return UsernamePasswordAuthenticationToken(user,null,ArrayList())
            }
            return null
        }
        return null
    }
}