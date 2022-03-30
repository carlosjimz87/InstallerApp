package com.carlosjimz87.installerapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import timber.log.Timber
import java.io.File


enum class InstallMethod {
    PROVIDER,
    INTENT_NEW_TASK

}

object Installer {

    private var method: InstallMethod = InstallMethod.PROVIDER

    fun method(method: InstallMethod): Installer {
        this.method = method
        return this
    }

    fun install(context: Context, filename: String, packageName: String) {
        try {
            val flag = when (method) {
                InstallMethod.PROVIDER -> Intent.FLAG_GRANT_READ_URI_PERMISSION
                InstallMethod.INTENT_NEW_TASK -> Intent.FLAG_ACTIVITY_NEW_TASK
            }


            AssetsManager.list(context).forEach { _fileName ->

                Timber.d("Attempt to install via $method of file $_fileName")
                if (filename == _fileName) {
                    val file = File("${Environment.getExternalStorageDirectory()}/apks/$_fileName")
                    Timber.d("File ${file.absolutePath} was created")
                    val uri =
                        FileProvider.getUriForFile(
                            context,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file
                        )
                    val intent = createIntent(uri, packageName, flag)
                    context.startActivity(intent)
                    Timber.d("Installation attempt of $packageName done")
                }
            }

        } catch (e: Exception) {
            val message = "Failed installation of $packageName (${e.message})"
            Timber.e(message)
        }
    }

    private fun createIntent(
        uri: Uri?,
        packageName: String,
        flag: Int
    ): Intent {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/$packageName")
            addFlags(flag)
        }
        Timber.d("Creating intent with uri:$uri package:$packageName and flag:$flag is $intent")
        return intent
    }

    fun appInstalledOrNot(context: Context, packageName: String): Boolean {
        val pm = context.packageManager;
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            Timber.d("App $packageName installed correctly")
            true
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e("App $packageName not found")
            false
        }
    }
}