package com.qifan.powerpermission.rx3

import android.os.Looper

internal fun checkMainThread() = check(Looper.myLooper() == Looper.getMainLooper()) {
    "Expected to be called on the main thread but was ${Thread.currentThread().name}"
}
