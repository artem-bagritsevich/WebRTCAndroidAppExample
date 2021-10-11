package com.android.webrtc.example.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.webrtc.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    // !!! Don't do it in prod, it's workaround to avoid memory leaks
    // We don't care for any cache, unfinished jobs, etc...
    override fun onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid())
        super.onBackPressed()
    }
}