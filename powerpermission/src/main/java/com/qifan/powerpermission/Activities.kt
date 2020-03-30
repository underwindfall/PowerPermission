package com.qifan.powerpermission

import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.core.PERMISSION_REQUEST_CODE
import com.qifan.powerpermission.data.hasAllGranted
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

fun AppCompatActivity.askPermissions(
    vararg permissions: Permission,
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null,
    callback: PermissionCallback
) {
    PowerPermission.init(this)
        .requestPermissions(
            permissions = *permissions,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate,
            callback = callback
        )
}


fun AppCompatActivity.askPermissionsAllGranted(
    vararg permissions: Permission,
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null,
    callback: (Boolean) -> Unit
) {
    PowerPermission.init(this)
        .requestPermissions(
            permissions = *permissions,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { permissionResults ->
            callback(permissionResults.hasAllGranted())
        }
}