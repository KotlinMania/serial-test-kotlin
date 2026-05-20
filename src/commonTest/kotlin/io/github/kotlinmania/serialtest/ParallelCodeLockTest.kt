// port-lint: source parallel_code_lock.rs
package io.github.kotlinmania.serialtest

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

class ParallelCodeLockTest {
    @Test
    fun unlockOnAssertSyncWithoutReturn() {
        assertFailsWith<AssertionError> {
            localParallelCore(listOf("unlock_on_assert_sync_without_return"), null) {
                fail("assertion failed")
            }
        }
        assertEquals(
            0,
            globalLocks().getValue("unlock_on_assert_sync_without_return").parallelCount(),
        )
    }

    @Test
    fun unlockOnAssertSyncWithReturn() {
        assertFailsWith<AssertionError> {
            localParallelCoreWithReturn(
                listOf("unlock_on_assert_sync_with_return"),
                null,
            ) {
                fail("assertion failed")
                Result.success(Unit)
            }
        }
        assertEquals(
            0,
            globalLocks().getValue("unlock_on_assert_sync_with_return").parallelCount(),
        )
    }

    @Test
    fun unlockOnAssertAsyncWithoutReturn() {
        suspend fun demoAssert() {
            fail("assertion failed")
        }

        suspend fun callSerialTestFn() {
            localAsyncParallelCore(listOf("unlock_on_assert_async_without_return"), null) {
                demoAssert()
            }
        }

        assertFailsWith<AssertionError> {
            runSuspendForTest { callSerialTestFn() }
        }
        assertEquals(
            0,
            globalLocks().getValue("unlock_on_assert_async_without_return").parallelCount(),
        )
    }

    @Test
    fun unlockOnAssertAsyncWithReturn() {
        suspend fun demoAssert(): Result<Unit> {
            fail("assertion failed")
            return Result.success(Unit)
        }

        suspend fun callSerialTestFn() {
            localAsyncParallelCoreWithReturn(
                listOf("unlock_on_assert_async_with_return"),
                null,
            ) {
                demoAssert()
            }
        }

        assertFailsWith<AssertionError> {
            runSuspendForTest { callSerialTestFn() }
        }
        assertEquals(
            0,
            globalLocks().getValue("unlock_on_assert_async_with_return").parallelCount(),
        )
    }
}

private fun runSuspendForTest(block: suspend () -> Unit) {
    var completed = false
    var failure: Throwable? = null
    block.startCoroutine(
        object : Continuation<Unit> {
            override val context = EmptyCoroutineContext

            override fun resumeWith(result: Result<Unit>) {
                completed = true
                failure = result.exceptionOrNull()
            }
        },
    )
    check(completed)
    failure?.let { throw it }
}
