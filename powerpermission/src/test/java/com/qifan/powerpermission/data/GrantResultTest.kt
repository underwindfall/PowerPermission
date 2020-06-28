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

import android.content.pm.PackageManager
import com.qifan.powerpermission.PowerPermission
import com.qifan.powerpermission.core.PermissionFragment
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek

object GrantResultTest : Spek({
    group("Grant Result Extension Test") {
        val mockedFragment by memoized { mockk<PermissionFragment>() }
        val mockedPermission by memoized { "mockedPermission" }
        test("on permission granted return GRANTED") {
            assertThat(
                mockedFragment.asGrantResult(
                    grantResult = PackageManager.PERMISSION_GRANTED,
                    permission = mockedPermission
                )
            ).isEqualTo(
                GrantResult.GRANTED
            )
        }
        test("on permission denied and not rational return PERMANENTLY_DENIED") {
            every {
                mockedFragment.shouldShowRequestPermissionRationale(mockedPermission)
            } returns false
            assertThat(
                mockedFragment.asGrantResult(
                    grantResult = PackageManager.PERMISSION_DENIED,
                    permission = mockedPermission
                )
            ).isEqualTo(
                GrantResult.PERMANENTLY_DENIED
            )
        }

        test("on permission denied and rational return RATIONAL") {
            mockkObject(PowerPermission)
            every {
                mockedFragment.shouldShowRequestPermissionRationale(mockedPermission)
            } returns true
            every { PowerPermission.configuration } returns DefaultConfiguration()
            assertThat(
                mockedFragment.asGrantResult(
                    grantResult = PackageManager.PERMISSION_DENIED,
                    permission = mockedPermission
                )
            ).isEqualTo(
                GrantResult.RATIONAL
            )
        }
    }
})
