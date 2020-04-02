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
