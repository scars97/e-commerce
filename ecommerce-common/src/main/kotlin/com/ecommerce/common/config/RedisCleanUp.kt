package com.ecommerce.common.config

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisCleanUp(
    private val redisTemplate: RedisTemplate<String, String>
) {

    private val log = LoggerFactory.getLogger(RedisCleanUp::class.java)

    fun execute() {
        val keys = redisTemplate.keys("*")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
            log.info("Delete All Keys")
        }
    }

}