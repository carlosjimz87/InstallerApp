package com.carlosjimz87.installerapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
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

    private val permissionData: MutableLiveData<Int> = MutableLiveData(PERMISSION_REQUEST_STORAGE)

    private lateinit var assetManager: AssetsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonDownload.isEnabled = false
        // init

        permissionData.observeForever {
            Timber.d("PERMISSION changed -> $it")
        }

        checkStoragePermission {
            start()
        }

    }

    private fun start() {
        assetManager = AssetsManager(this)
        assetManager.copyToExternal()
        buttonDownload.isEnabled = true
    }

    private fun checkStoragePermission(action: () -> Unit) {
        // Check if the storage permission has been granted
        if (checkSelfPermissionCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            Timber.w("PERMISSION GRANTED")

        } else {
            Timber.w("PERMISSION REQUIRED")
            // Permission is missing and must be requested.
            requestStoragePermission()
        }
    }


    private fun requestStoragePermission() {

        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


        } else {
//                        showSnackbarForRationale()
            requestPermissionsCompat(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
            Timber.w("PERMISSION RATIONALE 2")
//            start()
        }
    }

    private fun showSnackbarForRationale() {
        mainLayout.showSnackbar(
            getString(R.string.storage_access_required),
            Snackbar.LENGTH_INDEFINITE, getString(R.string.ok)
        ) {

            Timber.w("PERMISSION RATIONALE 1 $it")
            requestPermissionsCompat(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
        }
    }

}