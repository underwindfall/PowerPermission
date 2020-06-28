/**
 * Copyright (C) 2020 by Qifan YANG (@underwindfall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qifan.powerpermission.rationale

import android.app.Activity
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.data.RationaleData
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate
import com.qifan.powerpermission.rationale.delegate.dialog.DialogRationaleDelegate

/**
 * shortcut extension to display dialog rationale explains required permission
 * @param dialogTitle title of dialog
 * @param requiredPermission target permission when call [Fragment.shouldShowRequestPermissionRationale]
 * return true to display dialog
 * @param message to describe reason about requiring permission
 * @param positiveText to set string to [android.app.AlertDialog.BUTTON_POSITIVE]
 * @param negativeText to set string to [android.app.AlertDialog.BUTTON_NEGATIVE]
 */
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

/**
 * shortcut extension to display dialog rationale explains required permission
 * @param dialogTitle title of dialog
 * @param requiredPermissions list of target permission when call
 * [Fragment.shouldShowRequestPermissionRationale] return true to display dialog
 * @param message to describe reason about requiring permission
 * @param positiveText to set string to [android.app.AlertDialog.BUTTON_POSITIVE]
 * @param negativeText to set string to [android.app.AlertDialog.BUTTON_NEGATIVE]
 */
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

/**
 * shortcut extension to display dialog rationale explains required permission
 * @param dialogTitle title of dialog
 * @param requiredPermission target permission when call [Fragment.shouldShowRequestPermissionRationale]
 * return true to display dialog
 * @param message to describe reason about requiring permission
 * @param positiveText to set string to [android.app.AlertDialog.BUTTON_POSITIVE]
 * @param negativeText to set string to [android.app.AlertDialog.BUTTON_NEGATIVE]
 */
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

/**
 * shortcut extension to display dialog rationale explains required permission
 * @param dialogTitle title of dialog
 * @param requiredPermissions list of target permission when call
 * [Fragment.shouldShowRequestPermissionRationale] return true to display dialog
 * @param message to describe reason about requiring permission
 * @param positiveText to set string to [android.app.AlertDialog.BUTTON_POSITIVE]
 * @param negativeText to set string to [android.app.AlertDialog.BUTTON_NEGATIVE]
 */
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
