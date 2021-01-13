package com.homekit.home_first_and_kit.repostiory

import com.homekit.home_first_and_kit.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository("user")
interface UserRepository : JpaRepository<UserEntity, Long>{
    fun findByUsername(username: String): UserEntity?
}