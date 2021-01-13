package com.homekit.home_first_and_kit.model


import lombok.ToString
import javax.persistence.*

@Entity
@Table(name = "t_user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val user_id: Long?,

    @Column(name = "username", unique = true)
    var username: String?,

    @Column(name = "password")
    var password: String?,

    @Column(name = "firstname")
    var firstname: String?,

    @Column(name = "lastname")
    var lastname: String?,

    @Column(name = "email", unique = true)
    var email: String?,

    @Column(name = "birthDay")
    var birthDay: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status?,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "t_user_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "role_id")]
    )
    @ToString.Exclude
    var roles: MutableList<RoleEntity>?
) : AuditModel() {
    override fun toString(): String {
        return "UserEntity(user_id=$user_id, username=$username, password=$password, firstname=$firstname, lastname=$lastname, email=$email, birthDay=$birthDay, status=$status)"
    }
}
