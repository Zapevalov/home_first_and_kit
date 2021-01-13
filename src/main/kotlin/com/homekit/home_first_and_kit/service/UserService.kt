package com.homekit.home_first_and_kit.service

import com.homekit.home_first_and_kit.exception.ResourceNotFoundException
import com.homekit.home_first_and_kit.model.BoxEntity
import com.homekit.home_first_and_kit.model.Status
import com.homekit.home_first_and_kit.repostiory.UserRepository
import com.homekit.home_first_and_kit.model.UserEntity
import com.homekit.home_first_and_kit.repostiory.BoxRepository
import com.homekit.home_first_and_kit.repostiory.RoleRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
@Slf4j
class UserService(
    @Qualifier("user") @Autowired val userRepository: UserRepository,
    @Autowired val roleRepository: RoleRepository,
    @Autowired val passwordEncoder: BCryptPasswordEncoder,
    @Autowired val boxRepository: BoxRepository
) {
    private val log = LoggerFactory.getLogger(UserService::class.java)


    fun getAllUsers(): MutableIterable<UserEntity>{
        val findAll = userRepository.findAll()

        log.info("IN getAll - {} users found", findAll)
        //TODO Обработать ошибку получения
        return findAll
    }

    fun findUserById(id: Long): UserEntity {
        val result = userRepository.findById(id)
        if(result.isEmpty) {
            log.warn("IN getUserById - no user found by id: {}", id)
            throw ResourceNotFoundException()
        }

        return result.get()
    }

    fun findByUsername(username: String): UserEntity {
        val user: UserEntity = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User with username: $username not found")

        log.info("IN findByUsername - user: $user found by username: $username")
        return user
    }

    fun updateUser(id: Long, user: UserEntity) = userRepository
        .findById(id).get().let {
            it.birthDay = user.birthDay
            it.firstname = user.firstname
            it.password = user.password
            it.lastname = user.lastname

            userRepository.save(it)
        }

    fun delete(id: Long) {
        userRepository.deleteById(id)
        log.info("IN delete - user with id: {} successfully deleted")
    }

    fun register(user: UserEntity): UserEntity {
        val userRole = roleRepository.findByName("ROLE_USER")
        user.password = "{bcrypt}" + passwordEncoder.encode(user.password!!)
        user.roles = arrayListOf(userRole)
        user.status = Status.ACTIVE

        val registeredUser: UserEntity = userRepository.save(user)
        log.info("IN register - user: $registeredUser successfully registered")
        boxRepository.save(BoxEntity(name = "default", user = registeredUser, box_id = null))
        return registeredUser
    }


    //метод нужен нам для того, чтобы мы могли заавтовайрить BCryptPasswordEncoder
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}