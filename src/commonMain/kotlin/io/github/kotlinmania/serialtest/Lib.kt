// port-lint: source lib.rs
package io.github.kotlinmania.serialtest

/**
 * # SerialTest
 *
 * SerialTest allows for the creation of serialized Kotlin tests using serial
 * helpers.
 *
 * For example:
 *
 * ```kotlin
 * testSerialOne()
 * serial {
 *     // Do things
 * }
 *
 * serial("someKey") {
 *     // Do things
 * }
 *
 * parallel {
 *     // Do parallel things
 * }
 * ```
 *
 * Multiple tests with the serial helper are guaranteed to execute serially.
 * Ordering of the tests is not guaranteed. Other tests using the parallel
 * helper may run at the same time as each other, but not at the same time as a
 * test using the serial helper. Tests using neither helper may run at any time,
 * and no guarantees are made about their timing.
 *
 * For cases like documentation tests and integration tests where the tests are
 * run as separate processes, the file serial and file parallel helpers provide
 * similar properties based on file locking. There are no guarantees between one
 * test using serial or parallel and another using file serial or file parallel,
 * because they lock using different methods.
 *
 * ```kotlin
 * fileSerial {
 *     // Do things
 * }
 * ```
 *
 * The helpers can also be applied around all test functions in a surrounding
 * block by calling the helper at that block boundary.
 *
 * ```kotlin
 * serial {
 *     fun helper() {
 *         // Won't have serial applied unless called from inside the block.
 *     }
 *
 *     testBar()
 * }
 * ```
 *
 * All helpers support an optional crate argument for other generated code that
 * re-exports this package and supplies an import path.
 *
 * ## Feature flags
 *
 * Feature flags from the upstream crate are represented here as always
 * available Kotlin declarations.
 */

// Tracking file for upstream `src/lib.rs`. Crate-root callable exports are ported as
// their own Kotlin files rather than through a central re-export file. Downstream
// Kotlin callers import the defining functions directly.

// Upstream crate-root exports implemented in their defining Kotlin files:
// `localAsyncParallelCore` -> LocalAsyncParallelCore.kt.
// `localAsyncParallelCoreWithReturn` -> LocalAsyncParallelCoreWithReturn.kt.
// `localParallelCore` -> LocalParallelCore.kt.
// `localParallelCoreWithReturn` -> LocalParallelCoreWithReturn.kt.
// `localAsyncSerialCore` -> LocalAsyncSerialCore.kt.
// `localAsyncSerialCoreWithReturn` -> LocalAsyncSerialCoreWithReturn.kt.
// `localSerialCore` -> LocalSerialCore.kt.
// `localSerialCoreWithReturn` -> LocalSerialCoreWithReturn.kt.
// `fsAsyncSerialCore` -> FsAsyncSerialCore.kt.
// `fsAsyncSerialCoreWithReturn` -> FsAsyncSerialCoreWithReturn.kt.
// `fsSerialCore` -> FsSerialCore.kt.
// `fsSerialCoreWithReturn` -> FsSerialCoreWithReturn.kt.
// `isLockedFileSerially` -> IsLockedFileSerially.kt.
// `fsAsyncParallelCore` -> FsAsyncParallelCore.kt.
// `fsAsyncParallelCoreWithReturn` -> FsAsyncParallelCoreWithReturn.kt.
// `fsParallelCore` -> FsParallelCore.kt.
// `fsParallelCoreWithReturn` -> FsParallelCoreWithReturn.kt.
// `parallel` -> Parallel.kt.
// `serial` -> Serial.kt.
// `fileParallel` -> FileParallel.kt.
// `fileSerial` -> FileSerial.kt.
// `isLockedSerially` -> CodeLock.kt.

// Callers migrated:
//   RUST_CALLERS.md currently lists cross-repo demand for the crate-root serial symbol.
//   Kotlin downstream ports should import `io.github.kotlinmania.serialtest.serial`
//   directly and wrap their test bodies with that function.
