package com.homekit.home_first_and_kit.controller

import com.homekit.home_first_and_kit.model.BoxEntity
import com.homekit.home_first_and_kit.model.UserEntity
import com.homekit.home_first_and_kit.security.jwt.JwtTokenProvider
import com.homekit.home_first_and_kit.service.BoxService
import com.homekit.home_first_and_kit.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/box")
@RestController
class BoxController(
    @Autowired val boxService: BoxService,
    @Autowired val userService: UserService,
    val jwtTokenProvider: JwtTokenProvider,
) {

    @GetMapping
    fun getBoxes() = boxService.getAllBoxes()

    @GetMapping(path = ["/{id}"])
    fun getDrug(@PathVariable id: String) =
        boxService.getBoxById(id.toLong())

    @PostMapping
    fun addDrug(
        @RequestBody box: BoxEntity,
        @RequestHeader("Authorization") token: String
    ) {
        val user: UserEntity = userService.findByUsername(
            jwtTokenProvider.getUsername(token.substring(7))
        )
        boxService.insertBox(user.user_id!!, box)
    }


    @PutMapping(path = ["/{boxId}"])
    fun updateDrug(
        @RequestBody box: BoxEntity,
        @PathVariable boxId: String
    ) = boxService.updateBox(boxId.toLong(), box)

    @DeleteMapping(path = ["box/{id}"])
    fun deleteDrug(@PathVariable id: String) = boxService.deleteBox(id.toLong())


}