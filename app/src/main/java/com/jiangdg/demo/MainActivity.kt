/*
 * Copyright 2017-2022 Jiangdg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jiangdg.demo

import android.Manifest.permission.*
import android.os.Bundle
import android.os.PowerManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyf.immersionbar.ImmersionBar
import com.jiangdg.ausbc.utils.ToastUtils
import com.jiangdg.ausbc.utils.Utils
import com.jiangdg.demo.databinding.ActivityMainNewBinding

/**
 * Demos of camera usage with Bottom Navigation
 *
 * @author Created by jiangdg on 2021/12/27
 */
class MainActivity : AppCompatActivity() {
    private var mWakeLock: PowerManager.WakeLock? = null
    private var immersionBar: ImmersionBar? = null
    private lateinit var viewBinding: ActivityMainNewBinding
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        viewBinding = ActivityMainNewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        checkPermissionsAndSetup()
    }
    
    private fun checkPermissionsAndSetup() {
        val hasCameraPermission = PermissionChecker.checkSelfPermission(this, CAMERA)
        val hasStoragePermission = PermissionChecker.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
        
        if (hasCameraPermission != PermissionChecker.PERMISSION_GRANTED || 
            hasStoragePermission != PermissionChecker.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, CAMERA)) {
                ToastUtils.show(R.string.permission_tip)
            }
            ActivityCompat.requestPermissions(
                this,
                arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE, RECORD_AUDIO),
                REQUEST_CAMERA
            )
            return
        }
        
        setupBottomNavigation()
        // Start with Home fragment - set selected item to trigger navigation
        viewBinding.bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun setupBottomNavigation() {
        viewBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    navigateToHome()
                    updateToolbarTitle(getString(R.string.app_name_embsys))
                    true
                }
                R.id.nav_live_streaming -> {
                    navigateToLiveStreaming()
                    updateToolbarTitle(getString(R.string.live_streaming_title))
                    true
                }
                R.id.nav_video_management -> {
                    navigateToVideoManagement()
                    updateToolbarTitle(getString(R.string.video_management_title))
                    true
                }
                R.id.nav_settings -> {
                    navigateToSettings()
                    updateToolbarTitle(getString(R.string.settings_title))
                    true
                }
                else -> false
            }
        }
    }
    
    private fun updateToolbarTitle(title: String) {
        viewBinding.toolbarTitle.text = title
    }
    
    fun navigateToHome() {
        replaceFragment(HomeFragment())
    }
    
    fun navigateToLiveStreaming() {
        replaceFragment(DemoFragment())
    }
    
    fun navigateToVideoManagement() {
        replaceFragment(VideoManagementFragment())
    }
    
    fun navigateToSettings() {
        replaceFragment(SettingsFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        if (currentFragment?.javaClass == fragment.javaClass) {
            return // Already showing this fragment
        }
        currentFragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commitAllowingStateLoss()
    }

    override fun onStart() {
        super.onStart()
        mWakeLock = Utils.wakeLock(this)
    }

    override fun onStop() {
        super.onStop()
        mWakeLock?.apply {
            Utils.wakeUnLock(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA -> {
                val hasCameraPermission = PermissionChecker.checkSelfPermission(this, CAMERA)
                if (hasCameraPermission == PermissionChecker.PERMISSION_DENIED) {
                    ToastUtils.show(R.string.permission_tip)
                    return
                }
                setupBottomNavigation()
                navigateToHome()
            }
            REQUEST_STORAGE -> {
                val hasStoragePermission =
                    PermissionChecker.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                if (hasStoragePermission == PermissionChecker.PERMISSION_DENIED) {
                    ToastUtils.show(R.string.permission_tip)
                    return
                }
                // todo
            }
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        immersionBar = null
    }

    private fun setStatusBar() {
        immersionBar = ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .statusBarColor(R.color.embsys_primary_blue)
            .navigationBarColor(R.color.black)
            .fitsSystemWindows(true)
            .keyboardEnable(true)
        immersionBar?.init()
    }

    companion object {
        private const val REQUEST_CAMERA = 0
        private const val REQUEST_STORAGE = 1
    }
}