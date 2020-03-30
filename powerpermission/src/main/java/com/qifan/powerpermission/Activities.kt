package com.qifan.powerpermission

import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.core.PERMISSION_REQUEST_CODE
import com.qifan.powerpermission.data.hasAllGranted
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

/**
 * extension to simple usage about requesting permissions
 * @param permissions a list of permissions to be requested
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @param callback to return after execute requesting permissions
 */
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

/**
 * extension to simple usage about requesting permissions when all granted
 * @param permissions a list of permissions to be requested
 * @param requestCode request Permission CODE by default is [PERMISSION_REQUEST_CODE]
 * @param rationaleDelegate rationaleHandler to handle displaying reason interaction
 * @param callback to return after execute requesting permissions all granted or not
 */
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