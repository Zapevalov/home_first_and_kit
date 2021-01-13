package com.homekit.home_first_and_kit.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.homekit.home_first_and_kit.model.UserEntity
import javax.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserDto(
    val id: Long?,
    @get:NotBlank
    val username: String,
    @get:NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String,
    val firstname: String?,
    val lastname: String?,
    @get:NotBlank
    val email: String,
    val birthday: String?

) {
    fun toUser(): UserEntity {
        return UserEntity(
            null,
            this.username,
            this.password,
            this.firstname,
            this.lastname,
            this.email,
            this.birthday,
            null,
            null
        )
    }

    companion object {
        fun fromUser(user: UserEntity): UserDto {
            return UserDto(
                user.user_id,
                user.username!!,
                user.password!!,
                user.firstname,
                user.lastname,
                user.email!!,
                user.birthDay
            )
        }
    }


}
