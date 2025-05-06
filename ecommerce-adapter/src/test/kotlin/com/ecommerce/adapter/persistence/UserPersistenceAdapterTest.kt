package com.ecommerce.adapter.persistence

import com.ecommerce.adapter.config.IntegrateTestSupport
import com.ecommerce.application.port.out.UserPort
import com.ecommerce.domain.User
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

class UserPersistenceAdapterTest @Autowired constructor(
    private val sut: UserPort
): IntegrateTestSupport() {

    @Test
    fun commandUser() {
        // given
        val user = User(null, "성현", BigDecimal.ZERO, null, null)

        // when
        val result = sut.commandUser(user)

        // then
        assertThat(result.id).isOne()
        assertThat(result.createAt).isNotNull()
        assertThat(result.modifiedAt).isNotNull()
    }

}