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
package com.qifan.powerpermission.rx

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.R
import com.qifan.powerpermission.data.PermissionResult
import com.qifan.powerpermission.data.granted
import com.qifan.powerpermission.data.hasAllGranted
import com.qifan.powerpermission.data.hasPermanentDenied
import com.qifan.powerpermission.data.hasRational
import com.qifan.powerpermission.data.permanentDenied
import com.qifan.powerpermission.data.rational
import com.qifan.powerpermission.databinding.ActivityRx3ExmapleBinding
import com.qifan.powerpermission.rationale.createDialogRationale
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate
import com.qifan.powerpermission.rx3.askPermissionsRx
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class RxJava3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRx3ExmapleBinding
    private val simpleRequestButton get() = binding.simpleRequest
    private val resultText get() = binding.result

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val dialogRationaleDelegate: RationaleDelegate by lazy {
        createDialogRationale(
            R.string.rational_title,
            Manifest.permission.CAMERA,
            getString(R.string.permission_rational, Manifest.permission.CAMERA)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRx3ExmapleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSimpleRequestClick()
        supportActionBar?.setTitle(R.string.button_request_rxjava3)
    }

    private fun setUpSimpleRequestClick() {
        simpleRequestButton.setOnClickListener {
            askPermissionsRx(
                Manifest.permission.CAMERA,
                rationaleDelegate = dialogRationaleDelegate
            )
                .switchMapCompletable(::handlePermissionRequest)
                .subscribeAndLogError()
                .let(compositeDisposable::add)
        }
    }

    private fun handlePermissionRequest(permissionResult: PermissionResult): Completable {
        return Completable.fromCallable {
            when {
                permissionResult.hasAllGranted() -> {
                    doPermissionAllGrantedWork(permissionResult.granted())
                }
                permissionResult.hasRational() -> {
                    doPermissionReasonWork(permissionResult.rational())
                }
                permissionResult.hasPermanentDenied() -> {
                    doPermissionPermanentWork(permissionResult.permanentDenied())
                }
            }
        }
    }

    private fun doPermissionPermanentWork(permanentDenied: List<Permission>) {
        resultText.text = getString(R.string.permission_denied, permanentDenied)
    }

    private fun doPermissionReasonWork(rational: List<Permission>) {
        resultText.text = getString(R.string.permission_rational, rational)
    }

    private fun doPermissionAllGrantedWork(permissions: List<Permission>) {
        resultText.text = getString(R.string.permission_all_granted, permissions)
    }

    private fun logStreamError(exception: Throwable) {
        Log.e(RxJava2Activity::class.java.simpleName, exception.message, exception)
    }

    private fun Completable.subscribeAndLogError(): Disposable = subscribe({}, ::logStreamError)

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
