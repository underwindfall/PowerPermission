package com.qifan.powerpermission.rationale.delegate

import com.qifan.powerpermission.core.extension.warn

class RationaleActionCallback internal constructor(
    private var action: ((recheck: Boolean) -> Unit)?
) {
    operator fun invoke(recheck: Boolean) {
        if (action == null) {
            warn("Confirm callback invoked more than once, ignored after first invocation.")
        }
        action?.invoke(recheck)
        action = null
    }
}
