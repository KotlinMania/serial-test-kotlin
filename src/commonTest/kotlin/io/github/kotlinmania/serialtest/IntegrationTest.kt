// port-lint: tests tests.rs
package io.github.kotlinmania.serialtest

import kotlin.test.Test

class IntegrationTest {
    @Test
    fun testEmptySerialCall() {
        localSerialCore(listOf("beta"), null) {
            // Bar
        }
    }
}
