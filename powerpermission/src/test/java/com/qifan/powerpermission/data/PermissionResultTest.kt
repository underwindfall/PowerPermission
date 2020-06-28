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

import android.Manifest
import android.content.pm.PackageManager
import com.qifan.powerpermission.PowerPermission
import com.qifan.powerpermission.core.PermissionFragment
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PermissionResultTest : Spek({
    describe("Permission Result") {
        it("should invoke set constructor") {
            mockkObject(PowerPermission)
            every { PowerPermission.configuration } returns DefaultConfiguration()
            val mockedFragment = mockk<PermissionFragment>()
            every {
                mockedFragment.shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)
            } returns false
            every {
                mockedFragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)
            } returns true
            val result = PermissionResult(
                mockedFragment,
                setOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALENDAR
                ),
                intArrayOf(
                    PackageManager.PERMISSION_GRANTED,
                    PackageManager.PERMISSION_DENIED,
                    PackageManager.PERMISSION_DENIED
                )
            )
            assertThat(result).isEqualTo(
                PermissionResult(
                    setOf(
                        PermissionData(
                            data = Manifest.permission.CAMERA,
                            grantResult = GrantResult.GRANTED
                        ),
                        PermissionData(
                            data = Manifest.permission.CALL_PHONE,
                            grantResult = GrantResult.PERMANENTLY_DENIED
                        ),
                        PermissionData(
                            data = Manifest.permission.READ_CALENDAR,
                            grantResult = GrantResult.RATIONAL
                        )
                    )
                )
            )
        }
    }
})
