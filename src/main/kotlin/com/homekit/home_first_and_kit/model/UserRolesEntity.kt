package com.homekit.home_first_and_kit.model

import javax.persistence.*

@Entity
@Table(name = "t_user_roles")
data class UserRolesEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "user_id")
    val user_id: Long,

    @Column(name ="role_id")
    val role_id: Long
)