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

import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.core.extension.isRational

/**
 * Class for set value for Rationale permissions
 * @param rationalPermission target permission to display reason
 * e.g [android.Manifest.permission.ACCESS_COARSE_LOCATION]
 * @param rationalPermissions target list of permissions to display reason
 * e.g [listOf] [android.Manifest.permission.ACCESS_COARSE_LOCATION])
 * @param message display reason about requiring permission
 */
data class RationaleData internal constructor(
    private val rationalPermission: Permission?,
    private val rationalPermissions: List<Permission>?,
    val message: String
) {
    constructor(
        rationalPermission: Permission,
        message: String
    ) : this(rationalPermission, null, message)

    constructor(
        rationalPermissions: List<Permission>,
        message: String
    ) : this(null, rationalPermissions, message)

    internal fun getRationalePermission(): List<Permission> {
        return if (rationalPermissions.isNullOrEmpty()) {
            listOf(rationalPermission!!)
        } else {
            rationalPermissions
        }
    }

    /** @return `true` if given user permissions contains rational permissions. */
    @CheckResult
    internal fun shouldInvokeRational(
        fragment: Fragment,
        permissionResult: PermissionResult
    ): Boolean {
        return permissionResult.hasRational() && fragment.isRational(getRationalePermission())
    }
}
