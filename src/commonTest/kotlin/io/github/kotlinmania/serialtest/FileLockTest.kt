// port-lint: source file_lock.rs
package io.github.kotlinmania.serialtest

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FileLockTest {
    @Test
    fun assertSeriallyLockedWithoutName() {
        fsSerialCore(listOf(""), null) {
            assertTrue(isLockedFileSerially())
            assertFalse(isLockedFileSerially("no_such_name_assert_serially_locked_without_name"))
        }
    }

    @Test
    fun assertSeriallyLockedWithMultipleNames() {
        val name1 = "assert_serially_locked_with_multiple_names-NAME1"
        val name2 = "assert_serially_locked_with_multiple_names-NAME2"

        fsSerialCore(listOf(name1, name2), null) {
            assertTrue(isLockedFileSerially(name1))
            assertTrue(isLockedFileSerially(name2))
            assertFalse(isLockedFileSerially("no_such_name_assert_serially_locked_with_multiple_names"))
        }
    }

    @Test
    fun assertSeriallyLockedWhenActuallyLockedParallel() {
        val name1 = "assert_serially_locked_when_actually_locked_parallel-NAME1"
        val name2 = "assert_serially_locked_when_actually_locked_parallel-NAME2"

        fsParallelCore(listOf(name1, name2), null) {
            assertFalse(isLockedFileSerially(name1))
            assertFalse(isLockedFileSerially(name2))
            assertFalse(isLockedFileSerially("no_such_name_assert_serially_locked_when_actually_locked_parallel"))
        }
    }

    @Test
    fun assertSeriallyLockedOutsideSerialLock() {
        val name1 = "assert_serially_locked_outside_serial_lock-NAME1"
        val name2 = "assert_serially_locked_outside_serial_lock-NAME2"

        assertFalse(isLockedFileSerially(name1))
        assertFalse(isLockedFileSerially(name2))

        fsSerialCore(listOf(name1), null) {
        }

        assertFalse(isLockedFileSerially(name1))
        assertFalse(isLockedFileSerially(name2))
    }

    // Upstream `assert_serially_locked_in_different_thread` spawns a thread and
    // checks that a file lock held by the current thread is not visible from the
    // other thread. The Kotlin port's file lock implementation uses in-process
    // Locks objects without OS-level file locking (fslock), so cross-process and
    // cross-thread file lock semantics are not reproducible.
}
