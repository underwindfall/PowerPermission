package com.qifan.powerpermission.internal

import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.PermissionCallback
import com.qifan.powerpermission.RequestCode

const val PERMISSION_REQUEST_CODE = 1

data class PermissionParams(
    val permissions: List<Permission>,
    val requestCode: RequestCode,
    val callback: PermissionCallback
)
