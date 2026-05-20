// port-lint: source serial_file_lock.rs
package io.github.kotlinmania.serialtest

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class SerialFileLockTest {
    @Test
    fun testSerial() {
        fsSerialCore(listOf("test"), null) {
        }
    }

    @Test
    fun unlockOnAssertSyncWithoutReturn() {
        val lockPath = pathForName("serial_unlock_on_assert_sync_without_return")
        assertFailsWith<AssertionError> {
            fsSerialCore(
                listOf("serial_unlock_on_assert_sync_without_return"),
                lockPath,
            ) {
                throw AssertionError("assertion failed")
            }
        }
        assertFalse(Lock.isLocked(lockPath))
    }
}
