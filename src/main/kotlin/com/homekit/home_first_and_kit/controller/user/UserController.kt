package com.homekit.home_first_and_kit.controller.user

import com.homekit.home_first_and_kit.dto.UserDto
import com.homekit.home_first_and_kit.model.UserEntity
import com.homekit.home_first_and_kit.security.jwt.JwtTokenProvider
import com.homekit.home_first_and_kit.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RequestMapping("/api/v1/user")
@RestController
class UserController(
    val userService: UserService,
    val jwtTokenProvider: JwtTokenProvider) {

    @PostMapping(value = ["/register"])
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody @Valid userDto: UserDto): UserDto{
        return UserDto.fromUser(userService.register(userDto.toUser()))
    }

    //TODO убрать повторяемость
    @GetMapping
    fun getUser(@RequestHeader("Authorization") token: String){
        val user: UserEntity = userService.findByUsername(
            jwtTokenProvider.getUsername(token.substring(7))
        )
        UserDto.fromUser(userService.findUserById(user.user_id!!))
    }

    //TODO убрать повторяемость
    @PutMapping
    fun updateUser(@RequestHeader("Authorization") token: String,
                   @RequestBody userEntity: UserEntity){
        val user: UserEntity = userService.findByUsername(
            jwtTokenProvider.getUsername(token.substring(7))
        )
        UserDto.fromUser(userService.updateUser(user.user_id!!, userEntity))
    }
}