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
package com.qifan.powerpermission.rationale.delegate.dialog

import android.app.AlertDialog
import android.content.Context
import androidx.annotation.StringRes
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.data.RationaleData
import com.qifan.powerpermission.rationale.delegate.RationaleActionCallback
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate

/**
 * Implementation Dialog of RationaleDelegate
 */
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
