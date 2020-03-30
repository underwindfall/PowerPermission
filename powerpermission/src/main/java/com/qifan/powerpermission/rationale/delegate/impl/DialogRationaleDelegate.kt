package com.qifan.powerpermission.rationale.impl

import android.app.Activity
import android.app.AlertDialog
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.data.RationaleData
import com.qifan.powerpermission.rationale.RationaleActionCallback
import com.qifan.powerpermission.rationale.RationaleDelegate

class DialogRationaleDelegate constructor(
    private val context: Activity,
    @StringRes private val dialogTitle: Int,
    override val data: RationaleData
) : RationaleDelegate {
    private var dialog: AlertDialog? = null

    override fun displayRationale(
        vararg permission: Permission,
        message: String,
        actionCallback: RationaleActionCallback
    ) {
        dialog = AlertDialog.Builder(context)
            .setTitle(dialogTitle)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                (dialog as AlertDialog).setOnDismissListener(null)
                actionCallback(recheck = true)
            }
            .setOnDismissListener {
                actionCallback(recheck = false)
            }
            .setCancelable(false)
            .show()
    }

    override fun onDismissView() {
        dialog?.dismiss()
        dialog = null
    }
}


fun Fragment.createDialogRationale(
    @StringRes dialogTitle: Int,
    requiredPermission: Permission,
    message: String
): RationaleDelegate {
    return with(
        RationaleData(
            rationalPermission = requiredPermission,
            message = message
        )
    ) {
        DialogRationaleDelegate(
            context = requireActivity(),
            dialogTitle = dialogTitle,
            data = this
        )
    }
}

fun Fragment.createDialogRationale(
    @StringRes dialogTitle: Int,
    requiredPermissions: List<Permission>,
    message: String
): RationaleDelegate {
    return with(
        RationaleData(
            rationalPermissions = requiredPermissions,
            message = message
        )
    ) {
        DialogRationaleDelegate(
            context = requireActivity(),
            dialogTitle = dialogTitle,
            data = this
        )
    }
}


fun Activity.createDialogRationale(
    @StringRes dialogTitle: Int,
    requiredPermission: Permission,
    message: String
): RationaleDelegate {
    return with(
        RationaleData(
            rationalPermission = requiredPermission,
            message = message
        )
    ) {
        DialogRationaleDelegate(
            context = this@createDialogRationale,
            dialogTitle = dialogTitle,
            data = this
        )
    }
}