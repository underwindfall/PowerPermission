package com.qifan.powerpermission.data

import android.content.pm.PackageManager
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.internal.PermissionFragment

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
    forPermission: Permission
): GrantResult {
    return when (grantResult) {
        PackageManager.PERMISSION_GRANTED -> GrantResult.GRANTED
        else -> {
            if (shouldShowRequestPermissionRationale(forPermission)) {
                GrantResult.RATIONAL
            } else {
                GrantResult.PERMANENTLY_DENIED
            }
        }
    }
}
