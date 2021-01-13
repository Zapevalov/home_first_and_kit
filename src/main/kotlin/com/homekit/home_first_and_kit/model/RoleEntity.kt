package com.homekit.home_first_and_kit.model

import lombok.ToString
import javax.persistence.*

@Entity
@Table(name = "t_roles")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var role_id: Long,

    @Column(name = "name", unique = true)
    var name: String,

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ToString.Exclude
    var users: MutableList<UserEntity>
) : AuditModel(){

    override fun toString(): String {
        return "RoleEntity(role_id=$role_id, name='$name')"
    }
}