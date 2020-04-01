package com.qifan.powerpermission.rx

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.R
import com.qifan.powerpermission.data.*
import com.qifan.powerpermission.databinding.ActivityRx2ExmapleBinding
import com.qifan.powerpermission.rationale.createDialogRationale
import com.qifan.powerpermission.rationale.delegate.RationaleDelegate
import com.qifan.powerpermission.rx2.askPermissionsRx
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class RxJava2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityRx2ExmapleBinding
    private val simpleRequestButton get() = binding.simpleRequest
    private val rxBindingRequestButton get() = binding.requestRxBinding
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
        binding = ActivityRx2ExmapleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSimpleRequestClick()
        setUpRxBindingClick()
        supportActionBar?.setTitle(R.string.button_request_rxjava2)
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

    private fun setUpRxBindingClick() {
        rxBindingRequestButton.clicks()
            .throttleFirst(1L, TimeUnit.SECONDS)
            .flatMap {
                askPermissionsRx(
                    Manifest.permission.CAMERA,
                    rationaleDelegate = dialogRationaleDelegate
                )
            }
            .switchMapCompletable(::handlePermissionRequest)
            .subscribeAndLogError()
            .let(compositeDisposable::add)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
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
}
