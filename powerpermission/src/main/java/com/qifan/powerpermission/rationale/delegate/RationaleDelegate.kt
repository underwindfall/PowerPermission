package com.qifan.powerpermission.rationale.delegate

import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.core.extension.debug
import com.qifan.powerpermission.data.RationaleData

internal typealias PermissionRequest = (Array<out Permission>, Int) -> Unit

interface RationaleDelegate {
    val data: RationaleData

    fun showRationale(request: PermissionRequest, requestCode: Int) {
        displayRationale(
            permission = *data
                .getRationalePermission()
                .toTypedArray(),
            message = data.message,
            actionCallback = RationaleActionCallback { recheck ->
                if (recheck) {
                    onAcceptRecheckPermission(request, requestCode)
                } else {
                    onRefuseRecheckPermission()
                }
            }
        )
    }

    fun displayRationale(
        vararg permission: Permission,
        message: String,
        actionCallback: RationaleActionCallback
    )

    private fun onAcceptRecheckPermission(requestPermission: PermissionRequest, requestCode: Int) {
        debug("Permissions %s will been asked again", data.getRationalePermission())
        onDismissView()
        requestPermission(
            data
                .getRationalePermission()
                .toTypedArray(),
            requestCode
        )
    }

    private fun onRefuseRecheckPermission() {
        debug("Permissions %s are denied", data.getRationalePermission())
        onDismissView()
    }

    fun onDismissView()
}
