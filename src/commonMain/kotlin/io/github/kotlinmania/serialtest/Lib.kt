// port-lint: source lib.rs
package io.github.kotlinmania.serialtest

/**
 * # SerialTest
 *
 * SerialTest allows for the creation of serialized Kotlin tests using
 * serial helpers.
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
 * All helpers support an optional crate argument for other generated code that
 * re-exports this package and supplies an import path.
 *
 * ## Feature flags
 *
 * Feature flags from the upstream crate are represented here as always
 * available Kotlin declarations.
 */
