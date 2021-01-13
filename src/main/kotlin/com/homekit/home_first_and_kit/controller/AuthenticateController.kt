package com.homekit.home_first_and_kit.controller

import com.homekit.home_first_and_kit.dto.AuthenticationRequestDto
import com.homekit.home_first_and_kit.model.UserEntity
import com.homekit.home_first_and_kit.security.jwt.JwtTokenProvider
import com.homekit.home_first_and_kit.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/auth/"])
class AuthenticateController
@Autowired constructor(
    val jwtTokenProvider: JwtTokenProvider,
    val userService: UserService,
    val authenticationManager: AuthenticationManager
) {
    @PostMapping("login")
    fun login(@RequestBody requestDto: AuthenticationRequestDto): ResponseEntity<*>? {
        return try {
            val username: String = requestDto.username
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, requestDto.password))
            val user: UserEntity = userService.findByUsername(username)
            val token = jwtTokenProvider.createToken(username, user.roles)

            ResponseEntity.ok<Map<Any, Any?>>(hashMapOf("username" to username, "token" to token))
        } catch (e: AuthenticationException) {
            throw BadCredentialsException("Invalid username or password")
        }
    }
}