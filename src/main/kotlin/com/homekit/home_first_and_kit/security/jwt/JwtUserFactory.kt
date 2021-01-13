package com.homekit.home_first_and_kit.security.jwt

import com.homekit.home_first_and_kit.model.RoleEntity
import com.homekit.home_first_and_kit.model.Status
import com.homekit.home_first_and_kit.model.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class JwtUserFactory {
    companion object {
        fun create(user: UserEntity): JwtUser = JwtUser(
            user.user_id,
            user.username,
            user.firstname,
            user.lastname,
            user.password,
            user.email,
            user.status == Status.ACTIVE,
            user.getUpdatedAt(),
            authorities(userRoles = user.roles)
        )

        private fun authorities(userRoles: List<RoleEntity>?): List<GrantedAuthority>? {
            return userRoles?.map { role -> SimpleGrantedAuthority(role.name) }
        }
    }
}