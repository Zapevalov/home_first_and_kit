package com.homekit.home_first_and_kit.service

import com.homekit.home_first_and_kit.repostiory.BoxRepository
import com.homekit.home_first_and_kit.repostiory.UserRepository
import com.homekit.home_first_and_kit.exception.ResourceNotFoundException
import com.homekit.home_first_and_kit.model.BoxEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BoxService(
    @Qualifier("box") @Autowired val boxDao: BoxRepository,
    @Qualifier("user") @Autowired val userDao: UserRepository
) {
    fun getAllBoxes(): MutableIterable<BoxEntity> = boxDao.findAll()

    fun getBoxesByUserId(id: Long): List<BoxEntity> {
        return boxDao.getAllBoxesByUserId(id)
    }

    fun getBoxById(id: Long) = boxDao.findById(id)

    fun updateBox(boxId: Long, box: BoxEntity){
        boxDao.findById(boxId).map {
            it.name = box.name
            boxDao.save(it)
        }.orElseThrow { ResourceNotFoundException("Box with id " + boxId + "not found") }
    }

    fun deleteBox(id: Long) = boxDao.deleteById(id)

    fun insertBox(userId: Long, box: BoxEntity): BoxEntity {
        if(!userDao.existsById(userId))
            throw ResourceNotFoundException("User with id $userId not found")

        return userDao.findById(userId).map {
            box.user = it
            boxDao.save(box)
        }.get()
    }
}