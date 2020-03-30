package com.qifan.powerpermission

import androidx.fragment.app.Fragment
import com.qifan.powerpermission.core.PERMISSION_REQUEST_CODE
import com.qifan.powerpermission.data.hasAllGranted
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

fun Fragment.askPermissions(
    vararg permissions: Permission,
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null,
    callback: PermissionCallback
) {
    PowerPermission.init(requireActivity())
        .requestPermissions(
            permissions = *permissions,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate,
            callback = callback
        )
}


fun Fragment.askPermissionsAllGranted(
    vararg permissions: Permission,
    requestCode: RequestCode = PERMISSION_REQUEST_CODE,
    rationaleDelegate: RationaleDelegate? = null,
    callback: (Boolean) -> Unit
) {
    PowerPermission.init(requireActivity())
        .requestPermissions(
            permissions = *permissions,
            requestCode = requestCode,
            rationaleDelegate = rationaleDelegate
        ) { permissionResults ->
            callback(permissionResults.hasAllGranted())
        }
}