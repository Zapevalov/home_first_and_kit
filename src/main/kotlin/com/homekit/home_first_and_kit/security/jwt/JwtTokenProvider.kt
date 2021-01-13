package com.homekit.home_first_and_kit.security.jwt

import com.homekit.home_first_and_kit.model.RoleEntity
import com.homekit.home_first_and_kit.security.JwtUserDetailsService
import com.homekit.home_first_and_kit.security.jwt.exception.JwtAuthenticationException
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest
import org.springframework.security.crypto.factory.PasswordEncoderFactories

import org.springframework.security.crypto.password.PasswordEncoder
import io.jsonwebtoken.ExpiredJwtException

import javax.crypto.Cipher.PRIVATE_KEY

import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import java.lang.Exception


@Component
class JwtTokenProvider {
    private val log = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    @Value("\${jwt.token.secret}")
    private lateinit var jwtSecret: String

    @Value("\${jwt.token.expired}")
    private lateinit var validityInMilliseconds: String

    @Autowired
    private lateinit var userDetailsService: JwtUserDetailsService

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }


    @PostConstruct
    protected fun init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.toByteArray())
    }

    //TODO роль у пользователя есть всегда, нужно посмотреть, откуда идёт возможность нулла здесь
    fun createToken(username: String, roles: List<RoleEntity>?): String? {
        val claims: Claims = Jwts.claims().setSubject(username).apply {
            this["roles"] = roles?.map { it.name }
        }
        val dateNow = Date()
        val validity = Date(dateNow.time + validityInMilliseconds.toLong())

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(dateNow)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact()
    }

    fun getAuthentication(token: String): Authentication? {
        val userDetails = this.userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    fun isValidateToken(token: String): Boolean {
        val claims = Jwts.parser().setSigningKey(jwtSecret)
        try {
            return !claims.parseClaimsJws(token).body.expiration.before(Date())
        } catch (e: ExpiredJwtException) {
            return false
        } catch (e: SignatureException) {
            log.error(e.message)
        } catch (e: Exception) {
            println(" Some other exception in JWT parsing ")
        }
        return false
    }
}