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
            Timber.d("Attempt to install via $method")
            when (method) {
                InstallMethod.PROVIDER -> installViaFileProvider(context, filename, packageName)
                InstallMethod.INTENT_NEW_TASK -> installViaNewTaskIntent(
                    context,
                    filename,
                    packageName
                )
            }
        } catch (e: Exception) {
            val message = "Something went wrong with $packageName (${e.message})"
            Timber.e(message)
        }
    }

    private fun installViaFileProvider(context: Context, filename: String, packageName: String) {

        val intent = Intent(Intent.ACTION_VIEW)
        val file = File("${Environment.getExternalStorageDirectory()}/apks/$filename")
        val data =
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        intent.setDataAndType(data, "application/$packageName")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
        Timber.d("Installation of $packageName finished")

    }

    private fun installViaNewTaskIntent(context: Context, filename: String, packageName: String) {

        val promptInstall = Intent(Intent.ACTION_VIEW)
        promptInstall.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        promptInstall.setDataAndType(
            Uri.parse("file:///android_asset/$filename"),
            "application/$packageName"
        )
        context.startActivity(promptInstall)
        Timber.d("Installation of $packageName finished")
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