package com.example.vehicleapp.ui.login_activity

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.vehicleapp.MainApp
import com.example.vehicleapp.R
import com.example.vehicleapp.base.ActivityBase
import com.example.vehicleapp.base.repository.ResponseStatus.*
import com.example.vehicleapp.base.viewmodel.LoginViewModel
import com.example.vehicleapp.databinding.ActivityLoginBinding
import com.example.vehicleapp.di.shared.SharedStorage
import com.example.vehicleapp.ui.MainActivity
import com.example.vehicleapp.ui.login_activity.login_view.LoginUISource
import com.example.vehicleapp.utils.CustomProgressDialog
import com.example.vehicleapp.utils.gotoActivity
import com.example.vehicleapp.utils.isNetworkConnected
import com.example.vehicleapp.utils.obtainViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginActivity : ActivityBase(), LoginUISource {

    lateinit var bi: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel
    var permissionFlag = true
    var approval = false
    var requestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_login)
        bi.callback = this
//        bi.txtinstalldate.text = MainApp.appInfo.getAppInfo()
        viewModel = obtainViewModel(LoginViewModel::class.java, viewModelFactory)

        /*
        * Check if the user is already exist
        * */
        if (SharedStorage.getLogFlag(sharedPrefImpl)) {
            gotoActivity(MainActivity::class.java)
            finish()
        }

        /*
        * Check permission
        * */
        checkPermissions()

        /*
        * Get login confirmation from db. If it's null that means username or password - incorrect -
        * otherwise approve it
        *
        * */
        viewModel.loginResponse.observe(this, {
            when (it.status) {
                SUCCESS -> {
                    approval = true
                    it.data?.let { user ->
                        SharedStorage.setLogInUserName(sharedPrefImpl, user.username)
                    }
                    gotoActivity(MainActivity::class.java)
                }
                ERROR -> {
                    setPasswordIncorrect()
                    showProgress(false)
                }
                LOADING -> {
                    lifecycleScope.launch {
                        delay(1000)
                    }
                }
            }
        })

        viewModel.apiDownloadingDataProgress.observe(this, {
            if (it) {
                CustomProgressDialog.show(this, getString(R.string.downloadin_data))
            } else {
                CustomProgressDialog.dismiss()
            }
        })
    }

    /*
    * For uploading/downloading data, the network needs to work
    * */
    fun onSyncDataClick(v: View) {
        if (!isNetworkConnected(this)) {
            Toast.makeText(this, "Network connection not available!", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.downloadingUsers()
    }

    /*
    * Toggle password view
    * */
    fun onShowPasswordClick(v: View) {
        if (bi.password.transformationMethod == null) {
            bi.password.transformationMethod = PasswordTransformationMethod()
            bi.password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_close, 0, 0, 0)
        } else {
            bi.password.transformationMethod = null
            bi.password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_open, 0, 0, 0)
        }
    }

    /*
    * Click on login button, it works in steps:
    * 1. Check the permissions @checkPermissions
    * 3. If both of above conditions are okay then start coroutine to check login and proceed to MainActivity
    * */
    fun onLoginClick(v: View) {
        if (!permissionFlag)
            checkPermissions()
        else {
            // Store values at the time of the login attempt.
            val username = bi.username.text.toString()
            val password = bi.password.text.toString()
            showProgress(true)
            lifecycleScope.launch {
                delay(1000)
                if (!formValidation(username, password)) {
                    this.cancel()
                    showProgress(false)
                }
                val job = launch {
                    isLoginApproved(username, password)
                }
                job.join()
                if (approval) {
                    showProgress(false)
                    gotoActivity(MainActivity::class.java)
                }
            }

        }
    }

    /*
    * Visible progress dialog and hide whole layout when @param{show} is true and vice versa
    * */
    override fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

        bi.loginForm.visibility = if (show) View.GONE else View.VISIBLE
        bi.loginForm.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 0f else 1f
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                bi.loginForm.visibility = if (show) View.GONE else View.VISIBLE
            }
        })

        bi.loginProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
        bi.loginProgress.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 1f else 0f
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                bi.loginProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
            }
        })
    }

    /*
    * Validate username and password fields
    * */
    override fun formValidation(username: String, password: String): Boolean {
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            bi.password.error = "Invalid Password"
            bi.password.requestFocus()
            return false
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            bi.username.error = "Username is required."
            bi.username.requestFocus()
            return false
        }

        return true
    }

    /*
    * Set error on password
    * */
    override fun setPasswordIncorrect(error: String?) {
        bi.password.error = error ?: "Incorrect Password"
        bi.password.requestFocus()
    }

    /*
    * @isLoginApproved takes @params{username & password} and to see if it's testing user else -
    * pass it to viewmodel @getLoginInfoFromDB to check whether it exist in db or not
    * */
    override suspend fun isLoginApproved(username: String, password: String) {
        if (
            username == "test1234" && password == "test1234"
        ) {
            SharedStorage.setLogInUserName(sharedPrefImpl, "test_user")
            approval = true
        } else
            viewModel.getLoginInfoFromDB(username, password)
    }

    /*
    * Runtime permissions that user needs to be accept all of it otherwise -
    * it won't route to another activity
    * */
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionFlag = true
            return
        }
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                requestCode
            )
        } else if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode
            )
        }
        /* val options: Permissions.Options = Permissions.Options()
             .setRationaleDialogTitle("Permissions Required")
             .setSettingsDialogTitle("Warning")
         Permissions.check(this, permissions, null, options, object : PermissionHandler() {
             override fun onGranted() {
                 permissionFlag = true
             }
         })*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCode) {
            // Checking whether user granted the permission or not.
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                permissionFlag = false
            }
        }
    }

    companion object{
        val deviceId: String = try {
            Settings.Secure.getString(MainApp.applicationContext().contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            // Handle the exception, log it, or use a default value
            "UnknownDeviceID"
        }
    }

}