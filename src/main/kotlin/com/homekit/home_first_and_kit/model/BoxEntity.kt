package com.homekit.home_first_and_kit.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "t_box")
data class BoxEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val box_id: Long?,
    @Column(name = "name")
    var name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    @JsonIgnore
    var user: UserEntity?
): AuditModel()
