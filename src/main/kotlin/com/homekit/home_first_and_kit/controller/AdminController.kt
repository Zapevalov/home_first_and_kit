package com.homekit.home_first_and_kit.controller

import com.homekit.home_first_and_kit.dto.AdminUserDto
import com.homekit.home_first_and_kit.dto.UserDto
import com.homekit.home_first_and_kit.model.UserEntity
import com.homekit.home_first_and_kit.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/api/v1/admin/"])
class AdminController(val userService: UserService) {
    @GetMapping(value = ["users/{id}"])
    fun getUserById(@PathVariable(name = "id") id: Long): ResponseEntity<AdminUserDto?>? {
        val user: UserEntity =
            userService.findUserById(id)
        return ResponseEntity<AdminUserDto?>(AdminUserDto.fromUserEntity(user), HttpStatus.OK)
    }

    @GetMapping("/users")
    fun getUsers() = userService.getAllUsers().map { AdminUserDto.fromUserEntity(it) }

    @DeleteMapping(path = ["/users/{id}"])
    fun deleteUser(@PathVariable id: String) = userService.delete(id.toLong())

}