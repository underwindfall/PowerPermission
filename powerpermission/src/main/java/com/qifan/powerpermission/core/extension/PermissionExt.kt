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
package com.qifan.powerpermission.core.extension

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.CheckResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.data.GrantResult
import com.qifan.powerpermission.data.PermissionData

/**
 * helper function to check all permissions are granted or not.
 * @param permissions list of permissions e.g.[android.Manifest.permission.CALL_PHONE]
 * @return `true` if ALL given [permissions] have been granted.
 * */
@CheckResult
internal fun Context.isAllGranted(vararg permissions: Permission): Boolean {
    return permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}

/** @return `true` if given permission were granted. */
@CheckResult
internal fun PermissionData.isGranted(): Boolean {
    return grantResult == GrantResult.GRANTED
}

/** @return `true` if given permission were rational. */
@CheckResult
internal fun PermissionData.isRational(): Boolean {
    return grantResult == GrantResult.RATIONAL
}

/** @return `true` if given permission were permanent denied. */
@CheckResult
internal fun PermissionData.isPermanentDenied(): Boolean {
    return grantResult == GrantResult.PERMANENTLY_DENIED
}

/** @return `true` if given permissions contains rationale permission. */
@CheckResult
internal fun Fragment.isRational(permissions: List<Permission>): Boolean {
    return permissions.any { shouldShowRequestPermissionRationale(it) }
}
