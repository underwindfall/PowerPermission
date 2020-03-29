package com.qifan.powerpermission.internal.extension

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

internal fun FragmentActivity.transact(action: FragmentTransaction.() -> Unit) =
    supportFragmentManager.let {
        it.beginTransaction()
            .apply {
                action()
                commit()
            }
        it.executePendingTransactions()
    }

internal fun Fragment.transact(action: FragmentTransaction.(Context) -> Unit) {
    childFragmentManager.beginTransaction()
        .apply {
            action(activity ?: error("Fragment's activity is null."))
            commit()
        }
    childFragmentManager.executePendingTransactions()
}
