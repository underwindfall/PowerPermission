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
