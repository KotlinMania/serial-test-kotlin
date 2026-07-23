// port-lint: source serial_code_lock.rs
package io.github.kotlinmania.serialtest

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class SerialCodeLockTest {
    @Test
    fun testHammerCheckNewKey() {
        // Upstream spawns 100 threads behind a Barrier, each calling check_new_key
        // concurrently and collecting the mutex id. The test verifies that all
        // threads see the same (single) UniqueReentrantMutex entry. Kotlin
        // Multiplatform commonTest does not have a portable Barrier, so we call
        // checkNewKey 100 times sequentially — still verifying the single-entry
        // invariant, but not the concurrent-write race.
        val ids = mutableListOf<Int>()
        repeat(100) {
            checkNewKey("foo")
            ids += globalLocks().getValue("foo").id
        }
        assertEquals(100, ids.size)
        assertEquals(1, ids.toSet().size)
    }

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
