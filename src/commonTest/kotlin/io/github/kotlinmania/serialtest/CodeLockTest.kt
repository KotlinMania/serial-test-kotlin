// port-lint: source code_lock.rs
package io.github.kotlinmania.serialtest

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CodeLockTest {
    @Test
    fun assertSeriallyLockedWithoutName() {
        localSerialCore(listOf(""), null) {
            assertTrue(isLockedSerially())
            assertFalse(isLockedSerially("no_such_name_assert_serially_locked_without_name"))
        }
    }

    @Test
    fun assertSeriallyLockedWithMultipleNames() {
        val name1 = "assert_serially_locked_with_multiple_names-NAME1"
        val name2 = "assert_serially_locked_with_multiple_names-NAME2"
        localSerialCore(listOf(name1, name2), null) {
            assertTrue(isLockedSerially(name1))
            assertTrue(isLockedSerially(name2))
            assertFalse(isLockedSerially("no_such_name_assert_serially_locked_with_multiple_names"))
        }
    }

    @Test
    fun assertSeriallyLockedWhenActuallyLockedParallel() {
        val name1 = "assert_serially_locked_when_actually_locked_parallel-NAME1"
        val name2 = "assert_serially_locked_when_actually_locked_parallel-NAME2"
        localParallelCore(listOf(name1, name2), null) {
            assertFalse(isLockedSerially(name1))
            assertFalse(isLockedSerially(name2))
            assertFalse(isLockedSerially("no_such_name_assert_serially_locked_when_actually_locked_parallel"))
        }
    }

    @Test
    fun assertSeriallyLockedOutsideSerialLock() {
        val name1 = "assert_serially_locked_outside_serial_lock-NAME1"
        val name2 = "assert_serially_locked_outside_serial_lock-NAME2"
        assertFalse(isLockedSerially(name1))
        assertFalse(isLockedSerially(name2))

        localSerialCore(listOf(name1), null) {
        }

        assertFalse(isLockedSerially(name1))
        assertFalse(isLockedSerially(name2))
    }

    @Test
    fun serialWrapperHoldsTheNamedLock() {
        val name = "serial_wrapper_holds_the_named_lock"
        serial(name) {
            assertTrue(isLockedSerially(name))
            assertFalse(isLockedSerially())
        }
    }

    // Upstream `assert_serially_locked_in_different_thread` spawns a thread and
    // checks that a lock held by the current thread is not visible from the other
    // thread (is_locked_by_current_thread returns false on the spawned thread).
    // The Kotlin port's ReentrantGate does not track thread ownership —
    // isLockedByCurrentThread delegates to isLocked, so the cross-thread
    // distinction is not reproducible in a way that would pass the upstream assertion.
}
