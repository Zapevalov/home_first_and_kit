package com.homekit.home_first_and_kit.repostiory

import com.homekit.home_first_and_kit.model.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<RoleEntity, Long> {
    fun findByName(name: String): RoleEntity
}