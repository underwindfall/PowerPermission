package com.qifan.powerpermission.rationale.delegate.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import androidx.annotation.StringRes
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.data.RationaleData
import com.qifan.powerpermission.rationale.delegate.RationaleActionCallback
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

class DialogRationaleDelegate internal constructor(
    private val context: Context,
    @StringRes private val dialogTitle: Int,
    private val positiveText: String,
    private val negativeText: String?,
    override val data: RationaleData
) : RationaleDelegate {
    private var dialog: AlertDialog? = null

    override fun displayRationale(
        vararg permission: Permission,
        message: String,
        actionCallback: RationaleActionCallback
    ) {

        dialog = with(AlertDialog.Builder(context)) {
            setTitle(dialogTitle)
            setMessage(message)
            setPositiveButton(positiveText) { dialog, _ ->
                (dialog as AlertDialog).setOnDismissListener(null)
                actionCallback(recheck = true)
            }
            negativeText?.let {
                setNegativeButton(it) { _, _ ->
                    (dialog as AlertDialog).setOnDismissListener(null)
                    actionCallback(recheck = false)
                }
            }
            setOnDismissListener {
                actionCallback(recheck = false)
            }
            setCancelable(negativeText.isNullOrBlank())
            show()
        }
    }

    override fun onDismissView() {
        dialog?.dismiss()
        dialog = null
    }
}
