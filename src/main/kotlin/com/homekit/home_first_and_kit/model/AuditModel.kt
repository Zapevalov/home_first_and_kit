package com.homekit.home_first_and_kit.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*
import javax.persistence.*


@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
@JsonIgnoreProperties(
    value = ["createdAt", "updatedAt"],
    allowGetters = true
)
abstract class AuditModel: Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private var createdAt: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private var updatedAt: Date? = null

    open fun getCreatedAt(): Date? {
        return createdAt
    }

    open fun setCreatedAt(createdAt: Date?) {
        this.createdAt = createdAt
    }

    open fun getUpdatedAt(): Date? {
        return updatedAt
    }

    open fun setUpdatedAt(updatedAt: Date?) {
        this.updatedAt = updatedAt
    }
}