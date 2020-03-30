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
