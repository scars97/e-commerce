package com.ecommerce.adapter.config

import jakarta.annotation.PostConstruct
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Table
import jakarta.persistence.metamodel.EntityType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class DatabaseCleanUp {
    @PersistenceContext
    private val entityManager: EntityManager? = null
    private val tables: MutableList<String> = ArrayList()

    @PostConstruct
    fun afterPropertiesSet() {
        tables.addAll(
            entityManager!!.metamodel.entities.stream()
                .filter { entity: EntityType<*> ->
                    entity.javaType.isAnnotationPresent(
                        Entity::class.java
                    )
                }
                .map { entity: EntityType<*> ->
                    val tableAnnotation = entity.javaType.getAnnotation(Table::class.java)
                    val tableName = tableAnnotation?.name
                    if (!tableName.isNullOrBlank()) {
                        tableName.lowercase()
                    } else {
                        // fallback: 엔티티 이름에서 underscore 로 변환
                        entity.name
                            .replace(Regex("([a-z])([A-Z])"), "$1_$2")
                            .lowercase(Locale.getDefault())
                    }
                }
                .toList()
        )
    }

    @Transactional
    fun execute() {
        entityManager!!.flush()
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate()
        for (table in tables) {
            entityManager.createNativeQuery("TRUNCATE TABLE $table").executeUpdate()
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate()
    }
}
