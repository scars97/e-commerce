package com.ecommerce.adapter.config

import com.redis.testcontainers.RedisContainer
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

@Configuration
class TestContainerConfig {
    @PreDestroy
    fun preDestroy() {
        if (MYSQL_CONTAINER!!.isRunning()) {
            MYSQL_CONTAINER!!.stop()
        }
        if (REDIS_CONTAINER!!.isRunning()) {
            REDIS_CONTAINER!!.stop()
        }
    }

    companion object {
        var MYSQL_CONTAINER: MySQLContainer<*>? = null
        var REDIS_CONTAINER: RedisContainer? = null

        init {
            // mysql
            MYSQL_CONTAINER = MySQLContainer(DockerImageName.parse("mysql:8.0"))
                .apply {
                    withDatabaseName("ecommerce-test")
                    withUsername("test")
                    withPassword("test")
                }
            MYSQL_CONTAINER!!.start()
            System.setProperty("spring.datasource.url", MYSQL_CONTAINER!!.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=UTC")
            System.setProperty("spring.datasource.username", MYSQL_CONTAINER!!.getUsername())
            System.setProperty("spring.datasource.password", MYSQL_CONTAINER!!.getPassword())

            // redis
            REDIS_CONTAINER = RedisContainer(DockerImageName.parse("redis:6.2"))
                .apply {
                    withExposedPorts(6379)
                }
            REDIS_CONTAINER!!.start()
            System.setProperty("spring.data.redis.host", REDIS_CONTAINER!!.host)
            System.setProperty("spring.data.redis.port", REDIS_CONTAINER!!.getMappedPort(6379).toString())
        }
    }
}
