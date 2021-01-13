package com.homekit.home_first_and_kit.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.homekit.home_first_and_kit.model.Status
import com.homekit.home_first_and_kit.model.UserEntity
import jdk.jshell.Snippet

@JsonIgnoreProperties(ignoreUnknown = true)
data class AdminUserDto(
    val id: Long?,
    val username: String?,
    val firstname: String?,
    val lastname: String?,
    val email: String?,
    val status: String?
) {
    companion object{
        fun fromUserEntity(user: UserEntity): AdminUserDto{
            return AdminUserDto(
                user.user_id,
                user.username,
                user.firstname,
                user.lastname,
                user.email,
                user.status?.name
            )
        }

    }

    fun toUserEntity(): UserEntity{
        return UserEntity(
            this.id,
            this.username,
            null,
            this.firstname,
            this.lastname,
            this.email,
            null,
            this.status?.let { Status.valueOf(it) },
            null
        )
    }
}
