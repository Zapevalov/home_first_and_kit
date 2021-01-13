package com.homekit.home_first_and_kit.repostiory

import com.homekit.home_first_and_kit.model.BoxEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository("box")
interface BoxRepository: JpaRepository<BoxEntity, Long> {
    @Query("select * from t_box u where u.user_id = ?1", nativeQuery = true)
    fun getAllBoxesByUserId(userid: Long): List<BoxEntity>
}