package com.carlosjimz87.installerapp.managers

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.carlosjimz87.installerapp.R
import com.carlosjimz87.installerapp.ui.MainActivity
import com.carlosjimz87.installerapp.utils.showSnackbar
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

data class MPermission(private val n: String) {
    val name: String
        get() = n.takeWhile { it == '.' }
}

class PermissionManager(
    private val activity: MainActivity,
    private val MPermission: MPermission,
    val action: (permissionGranted: Boolean) -> Unit
) {

    private val context: Context = activity.baseContext

    private val requestPermissionLauncher =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        )
        { isGranted: Boolean ->
            if (isGranted) {
                Timber.w("PERMISSION GRANTED")
            } else {
                Timber.e("PERMISSION DENIED")
            }

            action(isGranted)
        }

    fun onRequestPermissionAction(view: View) {
        Timber.d("onRequestPermissionAction")
        when {
            // case permission granted
            ContextCompat.checkSelfPermission(
                context,
                MPermission.name
            ) == PackageManager.PERMISSION_GRANTED -> {

                Timber.w("PERMISSION GRANTED")
                view.showSnackbar(
                    context.getString(R.string.permission_granted, MPermission),
                    Snackbar.LENGTH_INDEFINITE,
                    null,
                    null
                )
                action(true)
            }

            // case should show permission rationale
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                MPermission.name
            ) -> {
                Timber.w("REQUEST RATIONALE TO SHOW")
                view.showSnackbar(
                    context.getString(R.string.permission_required),
                    Snackbar.LENGTH_INDEFINITE,
                    context.getString(R.string.ok)
                ) {
                    Timber.w("REQUEST PERMISSION LAUNCHER")
                    requestPermissionLauncher.launch(
                        MPermission.name
                    )
                }
            }

            // case permission hasn't been asked
            else -> {
                requestPermissionLauncher.launch(
                    MPermission.name
                )
            }
        }
    }
}