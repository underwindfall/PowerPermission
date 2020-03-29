package com.qifan.powerpermission.data

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.qifan.powerpermission.Permission

enum class GrantResult {
    GRANTED,
    DENIED,
    PERMANENTLY_DENIED
}

internal fun Int.asGrantResult(
    forPermission: Permission,
    activity: Activity
): GrantResult {
    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, forPermission)) {
        return GrantResult.PERMANENTLY_DENIED
    }
    return when (this) {
        PackageManager.PERMISSION_GRANTED -> GrantResult.GRANTED
        else -> GrantResult.DENIED
    }
}

internal fun IntArray.mapGrantResults(
    permissions: Set<Permission>,
    activity: Activity
): List<GrantResult> {
    return mapIndexed { index, grantResult ->
        val permission: Permission = permissions.elementAt(index)
        grantResult.asGrantResult(permission, activity)
    }
}
