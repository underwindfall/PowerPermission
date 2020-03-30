package com.qifan.powerpermission.data

import com.qifan.powerpermission.Permission

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
}
