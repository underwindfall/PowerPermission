package com.qifan.powerpermission.rx

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qifan.powerpermission.Permission
import com.qifan.powerpermission.R
import com.qifan.powerpermission.data.*
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
