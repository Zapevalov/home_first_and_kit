package com.homekit.home_first_and_kit.repostiory

import com.homekit.home_first_and_kit.model.MedicineEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository("medicine")
interface MedicineRepository : CrudRepository<MedicineEntity, Long> {
    @Query(
        value = "SELECT * FROM firstkit.public.t_medicine m WHERE m.box_id = ?1",
        nativeQuery = true
    )
    fun findByBoxId(boxId: Long): List<MedicineEntity>
}