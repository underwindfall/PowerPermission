/**
 * Copyright (C) 2020 by Qifan YANG (@underwindfall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
