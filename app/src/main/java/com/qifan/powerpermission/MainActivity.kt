package com.qifan.powerpermission

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_IMAGE_CAPTURE = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test.setOnClickListener { showCamera() }
    }

    private fun showCamera() {
        PowerPermission.init(this)
            .requestPermission(Manifest.permission.CAMERA) { rationalList, grantedList, deniedList ->
                when {
                    grantedList.contains(Manifest.permission.CAMERA) -> startCamera()
                    rationalList.isNotEmpty() -> Toast.makeText(
                        this,
                        "camera permission required $rationalList",
                        Toast.LENGTH_LONG
                    ).show()
                    deniedList.isNotEmpty() -> Toast.makeText(
                        this,
                        "camera permission denied $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}
