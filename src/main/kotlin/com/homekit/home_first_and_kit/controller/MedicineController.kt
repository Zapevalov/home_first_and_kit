package com.homekit.home_first_and_kit.controller

import com.homekit.home_first_and_kit.model.MedicineEntity
import com.homekit.home_first_and_kit.model.UserEntity
import com.homekit.home_first_and_kit.security.jwt.JwtTokenProvider
import com.homekit.home_first_and_kit.service.BoxService
import com.homekit.home_first_and_kit.service.MedicineService
import com.homekit.home_first_and_kit.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
class MedicineController(
    @Autowired val medicineService: MedicineService,
    val jwtTokenProvider: JwtTokenProvider,
    @Autowired val userService: UserService,
    @Autowired val boxService: BoxService
) {

    @GetMapping(path = ["/api/v1/box/{id}/medicines"])
    fun getMedicinesInBox(
        @PathVariable id: String
    ): List<MedicineEntity> {
        return medicineService.getMedicinesByBox(id.toLong())
    }

//    @GetMapping(path = ["{id}"])
//    fun getAllMedicine(@PathVariable id: Int) =
//        medicineService.getMedicineById(id.toLong())

    @PostMapping(path = ["/api/v1/medicine"])
    fun addMedicine(
        @RequestHeader("Authorization") token: String,
        @RequestBody medicine: MedicineEntity,
        @RequestHeader("boxId") boxId: Long?
    ) {
        if (boxId == null) {
            val user: UserEntity = userService.findByUsername(
                jwtTokenProvider.getUsername(token.substring(7))
            )
            val id = boxService.getBoxesByUserId(user.user_id!!).first { it.name == "default" }.box_id!!
            medicineService.insertMedicine(id, medicine)
        } else {
            medicineService.insertMedicine(boxId, medicine)
        }
    }


    @PutMapping(path = ["/api/v1/medicine/{id}"])
    fun updateMedicine(
        @PathVariable id: String,
        @RequestBody medicine: MedicineEntity,
    ) = medicineService.updateMedicine(id.toLong(), medicine)

    @DeleteMapping(path = ["/api/v1/medicine/{id}"])
    fun deleteMedicine(@PathVariable id: String) = medicineService.deleteMedicine(id.toLong())


}