package com.carlosjimz87.installerapp

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


val APPS = mapOf(
    "Files/other_app.apk" to "com.onthespot.androidplayer",
    "Files/signed_app.apk" to "com.onthespot.system"
)

class MainActivity : AppCompatActivity() {
    companion object {

        const val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (verifyStoragePermissions(this)) {
        AssetsManager.copyToExternal(this)
//        }

//        APPS.forEach { (fileName, packageName) ->
//
//            Installer.install(this, fileName, packageName)
//        }
    }

//    private fun verifyStoragePermissions(activity: Activity?): Boolean {
//        // Check if we have write permission
//        val permission1 = ActivityCompat.checkSelfPermission(
//            activity!!,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//        val permission2 = ActivityCompat.checkSelfPermission(
//            activity,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        )
//        return when (permission1 * permission2) {
//            1 -> true
//            else -> false
//        }
//    }
}