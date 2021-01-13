package com.homekit.home_first_and_kit.service

import com.homekit.home_first_and_kit.repostiory.BoxRepository
import com.homekit.home_first_and_kit.repostiory.MedicineRepository
import com.homekit.home_first_and_kit.exception.ResourceNotFoundException
import com.homekit.home_first_and_kit.model.MedicineEntity
import com.homekit.home_first_and_kit.security.JwtUserDetailsService
import com.homekit.home_first_and_kit.security.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class MedicineService(
    @Qualifier("medicine")
    @Autowired val medicineDao: MedicineRepository,
    @Qualifier("box") @Autowired val boxService: BoxRepository
) {
    fun getMedicines(): MutableIterable<MedicineEntity> {
        return medicineDao.findAll()
    }

    fun getMedicinesByBox(boxId: Long): List<MedicineEntity> {
        return medicineDao.findByBoxId(boxId)
    }

    fun getMedicineById(id: Long) = medicineDao.findById(id)

    fun updateMedicine(med_id: Long, medicine: MedicineEntity): MedicineEntity? {
        return medicineDao.findById(med_id).map {
            it.description = medicine.description
            it.name = medicine.name
            medicineDao.save(it)
        }.orElseThrow { ResourceNotFoundException("Medicine with id " + med_id + "not found") }
    }

    fun deleteMedicine(id: Long) = medicineDao.deleteById(id)

    fun insertMedicine(boxId: Long, medicine: MedicineEntity): MedicineEntity? {
        return boxService.findById(boxId).map {
            medicine.box = it
            medicineDao.save(medicine)
        }.get()
    }

}