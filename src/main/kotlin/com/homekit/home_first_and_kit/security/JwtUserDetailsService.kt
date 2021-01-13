package com.homekit.home_first_and_kit.security

import com.homekit.home_first_and_kit.security.jwt.JwtUserFactory
import com.homekit.home_first_and_kit.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(@Autowired val userService: UserService) : UserDetailsService {
    private val log = LoggerFactory.getLogger(JwtUserDetailsService::class.java)

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByUsername(username)

        val jwtUser = JwtUserFactory.create(user)
        log.info("IN loadUserByUsername - user with username: $username successfully loaded")

        return jwtUser
    }
}