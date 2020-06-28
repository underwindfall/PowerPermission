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
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.core.PermissionFragment
import com.qifan.powerpermission.core.extension.debug

/**
 * enum grant result to represent status of permission grant
 */
enum class GrantResult {
    GRANTED,
    RATIONAL,
    PERMANENTLY_DENIED
}

internal fun PermissionFragment.asGrantResult(
    grantResult: Int,
    permission: Permission
): GrantResult {
    return when (grantResult) {
        PackageManager.PERMISSION_GRANTED -> GrantResult.GRANTED
        else -> {
            if (shouldShowRequestPermissionRationale(permission)) {
                debug("what the fuck $permission")
                GrantResult.RATIONAL
            } else {
                GrantResult.PERMANENTLY_DENIED
            }
        }
    }
}
