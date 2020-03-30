package com.qifan.powerpermission.rationale

import android.app.Activity
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.data.RationaleData
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate
import com.qifan.powerpermission.rationale.delegate.dialog.DialogRationaleDelegate

fun Fragment.createDialogRationale(
    @StringRes dialogTitle: Int,
    requiredPermission: Permission,
    message: String,
    positiveText: String = getString(android.R.string.ok),
    negativeText: String? = null
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
            data = this,
            positiveText = positiveText,
            negativeText = negativeText
        )
    }
}

fun Fragment.createDialogRationale(
    @StringRes dialogTitle: Int,
    requiredPermissions: List<Permission>,
    message: String,
    positiveText: String = getString(android.R.string.ok),
    negativeText: String? = null
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
            data = this,
            positiveText = positiveText,
            negativeText = negativeText
        )
    }
}

fun Activity.createDialogRationale(
    @StringRes dialogTitle: Int,
    requiredPermission: Permission,
    message: String,
    positiveText: String = getString(android.R.string.ok),
    negativeText: String? = null
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
            data = this,
            positiveText = positiveText,
            negativeText = negativeText
        )
    }
}

fun Activity.createDialogRationale(
    @StringRes dialogTitle: Int,
    requiredPermissions: List<Permission>,
    message: String,
    positiveText: String = getString(android.R.string.ok),
    negativeText: String? = null
): RationaleDelegate {
    return with(
        RationaleData(
            rationalPermissions = requiredPermissions,
            message = message
        )
    ) {
        DialogRationaleDelegate(
            context = this@createDialogRationale,
            dialogTitle = dialogTitle,
            data = this,
            positiveText = positiveText,
            negativeText = negativeText
        )
    }
}
