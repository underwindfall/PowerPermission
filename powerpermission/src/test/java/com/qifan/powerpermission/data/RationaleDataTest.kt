package com.qifan.powerpermission.data

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek

object RationaleDataTest : Spek({
    group("Rationale Data") {
        val mockedPermission by memoized { "mockedPermission" }
        val mockedPermissions by memoized { listOf("mockedPermission", "mockedPermission2") }
        val mockedMessage by memoized { "mockedMessage" }
        group("RationaleData constructor") {
            test("Invoke permission message constructor") {
                assertThat(
                    RationaleData(
                        rationalPermission = mockedPermission,
                        message = mockedMessage
                    )
                ).isEqualTo(
                    RationaleData(
                        rationalPermission = "mockedPermission",
                        rationalPermissions = null,
                        message = "mockedMessage"
                    )
                )
            }

            test("Invoke permissions message constructor") {
                assertThat(
                    RationaleData(
                        rationalPermissions = mockedPermissions,
                        message = mockedMessage
                    )
                ).isEqualTo(
                    RationaleData(
                        rationalPermission = null,
                        rationalPermissions = listOf("mockedPermission", "mockedPermission2"),
                        message = "mockedMessage"
                    )
                )
            }
        }
    }
})
