package com.carlosjimz87.installerapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carlosjimz87.installerapp.R
import com.carlosjimz87.installerapp.managers.AssetsManager
import com.carlosjimz87.installerapp.utils.Constants.PERMISSION_REQUEST_STORAGE
import com.carlosjimz87.installerapp.utils.checkSelfPermissionCompat
import com.carlosjimz87.installerapp.utils.requestPermissionsCompat
import com.carlosjimz87.installerapp.utils.shouldShowRequestPermissionRationaleCompat
import com.carlosjimz87.installerapp.utils.showSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init
        val assetManager = AssetsManager(this)

        checkStoragePermission()

        assetManager.copyToExternal()

    }

    private fun checkStoragePermission() {
        // Check if the storage permission has been granted
        if (checkSelfPermissionCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("Permission granted")

        } else {
            Timber.w("Permission required")
            // Permission is missing and must be requested.
            requestStoragePermission()
        }
    }


    private fun requestStoragePermission() {

        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            mainLayout.showSnackbar(
                getString(R.string.storage_access_required),
                Snackbar.LENGTH_INDEFINITE, getString(R.string.ok)
            ) {
                requestPermissionsCompat(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_STORAGE
                )
            }

        } else {
            requestPermissionsCompat(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
        }
    }

}