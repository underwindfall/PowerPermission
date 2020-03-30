package com.qifan.powerpermission.rationale

import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.data.RationaleData
import com.qifan.powerpermission.internal.extension.debug

interface RationaleDelegate {
    val data: RationaleData

    fun showRationale() {
        displayRationale(
            permission = *data
                .getRationalePermission()
                .toTypedArray(),
            message = data.message,
            actionCallback = RationaleActionCallback { recheck ->
                if (recheck) {
                    onAcceptRecheckPermission()
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

    private fun onAcceptRecheckPermission() {
        debug("Permissions %s will been asked again", data.getRationalePermission())
        onDismissView()
    }

    private fun onRefuseRecheckPermission() {
        debug("Permissions %s are denied", data.getRationalePermission())
        onDismissView()
    }

    fun onDismissView()
}
