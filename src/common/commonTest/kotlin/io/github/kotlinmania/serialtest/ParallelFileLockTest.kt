// port-lint: tests parallel_file_lock.rs
package io.github.kotlinmania.serialtest

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ParallelFileLockTest {
    fun unlockOk(lockPath: String) {
        val lock = Lock.new(lockPath)
        assertEquals(0, lock.parallelCount)
        lock.endSerial()
    }

    suspend fun demoAssert(): Unit = throw AssertionError("assertion failed")

    suspend fun callSerialTestFn(lockPath: String) {
        fsAsyncParallelCore(
            listOf("unlock_on_assert_async_without_return"),
            lockPath,
        ) {
            demoAssert()
        }
    }

    @Test
    fun unlockOnAssertSyncWithoutReturn() {
        val lockPath = pathForName("parallel_unlock_on_assert_sync_without_return")
        assertFailsWith<AssertionError> {
            fsParallelCore(
                listOf("parallel_unlock_on_assert_sync_without_return"),
                lockPath,
            ) {
                throw AssertionError("assertion failed")
            }
        }
        unlockOk(lockPath)
    }

    @Test
    fun unlockOnAssertSyncWithReturn() {
        val lockPath = pathForName("unlock_on_assert_sync_with_return")
        assertFailsWith<AssertionError> {
            fsParallelCoreWithReturn(
                listOf("unlock_on_assert_sync_with_return"),
                lockPath,
            ) {
                throw AssertionError("assertion failed")
            }
        }
        unlockOk(lockPath)
    }

    @Test
    fun unlockOnAssertAsyncWithoutReturn() {
        val lockPath = pathForName("unlock_on_assert_async_without_return")

        assertFailsWith<AssertionError> {
            runSuspendForFileTest { callSerialTestFn(lockPath) }
        }
        unlockOk(lockPath)
    }

    @Test
    fun unlockOnAssertAsyncWithReturn() {
        val lockPath = pathForName("unlock_on_assert_async_with_return")

        assertFailsWith<AssertionError> {
            runSuspendForFileTest {
                fsAsyncParallelCoreWithReturn(
                    listOf("unlock_on_assert_async_with_return"),
                    lockPath,
                ) {
                    demoAssert()
                    Result.success(Unit)
                }
            }
        }
        unlockOk(lockPath)
    }
}

private fun runSuspendForFileTest(block: suspend () -> Unit) {
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
