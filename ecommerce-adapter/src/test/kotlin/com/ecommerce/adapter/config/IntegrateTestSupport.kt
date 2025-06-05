package com.ecommerce.adapter.config

import com.ecommerce.common.config.RedisCleanUp
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class IntegrateTestSupport {

    @Autowired
    private lateinit var databaseCleanUp: DatabaseCleanUp

    @Autowired
    private lateinit var redisCleanUp: RedisCleanUp

    @BeforeEach
    fun execute() {
        databaseCleanUp.execute()
        redisCleanUp.execute()
    }

}