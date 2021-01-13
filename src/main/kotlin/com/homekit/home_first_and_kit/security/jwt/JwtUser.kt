package com.homekit.home_first_and_kit.security.jwt

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class JwtUser(
     val id: Long?,
     val uname: String?,
     val firstname: String?,
     val lastname: String?,
     val pass: String?,
     val email: String?,
     val enabled: Boolean,
     val lastPasswordResetDate: Date?,
     val grantedAuthorities: Collection<GrantedAuthority>?
): UserDetails {

    override fun getAuthorities() = grantedAuthorities

    @JsonIgnore
    override fun getPassword() = pass

    override fun getUsername() = uname

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}