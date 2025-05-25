package com.ecommerce.common.lock

import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class DistributeLockExecutor(
    private val redissonClient: RedissonClient,
    private val functionalForTransaction: FunctionalForTransaction
) {

    private val log = LoggerFactory.getLogger(DistributeLockExecutor::class.java)

    fun <T> lockWithTransaction(key: String, waitTime: Long, leaseTime: Long, action: () -> T): T {
        val rLock = redissonClient.getLock(key)
        log.info("Start Lock : $key")

        return try {
            if (!rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)) {
                throw RuntimeException("락 획득 실패 : $key")
            }

            functionalForTransaction.proceed(action)
        } catch (e: InterruptedException) {
            throw InterruptedException()
        } finally {
            try {
                if (rLock.isLocked && rLock.isHeldByCurrentThread) {
                    rLock.unlock()
                    log.info("Finish Lock : $key")
                }
            } catch (e: IllegalMonitorStateException) {
                throw RuntimeException("Already UnLock : $key")
            }
        }
    }

}