package com.ecommerce.adapter.config

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class IntegrateTestSupport {

    @Autowired
    private lateinit var databaseCleanUp: DatabaseCleanUp

    @BeforeEach
    fun execute() {
        databaseCleanUp.execute()
    }

}