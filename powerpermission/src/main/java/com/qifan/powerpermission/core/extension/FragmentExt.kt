package com.qifan.powerpermission.core.extension

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

/**
 * add fragment transaction
 * @param action dsl [FragmentTransaction]
 * @return Returns true if there were any pending transactions to be executed
 */
internal fun FragmentActivity.transact(action: FragmentTransaction.() -> Unit) =
    supportFragmentManager.let {
        it.beginTransaction()
            .apply {
                action()
                commit()
            }
        it.executePendingTransactions()
    }

/**
 * add fragment transaction
 * @param action dsl [FragmentTransaction]
 * @return Returns true if there were any pending transactions to be executed
 */
internal fun Fragment.transact(action: FragmentTransaction.(Context) -> Unit) {
    childFragmentManager.beginTransaction()
        .apply {
            action(activity ?: error("Fragment's activity is null."))
            commit()
        }
    childFragmentManager.executePendingTransactions()
}
