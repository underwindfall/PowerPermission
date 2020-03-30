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
