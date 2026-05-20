// port-lint: source serial_code_lock.rs
package io.github.kotlinmania.serialtest

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class SerialCodeLockTest {
    @Test
    fun unlockOnAssert() {
        assertFailsWith<AssertionError> {
            localSerialCore(listOf("assert"), null) {
                throw AssertionError("assertion failed")
            }
        }
        assertFalse(globalLocks().getValue("assert").isLocked())
    }
}
