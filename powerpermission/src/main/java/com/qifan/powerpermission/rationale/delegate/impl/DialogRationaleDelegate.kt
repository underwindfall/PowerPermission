package com.qifan.powerpermission.rationale.delegate.impl

import android.app.Activity
import android.app.AlertDialog
import androidx.annotation.StringRes
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.data.RationaleData
import com.qifan.powerpermission.rationale.delegate.RationaleActionCallback
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

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