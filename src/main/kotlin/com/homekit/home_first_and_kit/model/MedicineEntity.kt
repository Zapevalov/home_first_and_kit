package com.homekit.home_first_and_kit.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_medicine")
data class MedicineEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val medicine_id: Long,
    @Column(name = "name")
    var name: String,
    @Column(name = "description")
    var description: String?,
    var count: Int,
    var expirationDate: Date,
    @Enumerated(EnumType.STRING)
    var type: MedicineType,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "box_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    var box: BoxEntity?
): AuditModel()