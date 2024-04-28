package com.example.player

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.player.databinding.ActivityMainBinding
import com.example.player.ext.doOnLifecycle


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestMyPermissions()

        player = Player(this)

        player.initResId(R.raw.demo)

        // Example of a call to a native method
        binding.button.setOnClickListener {
            if (player.isPlaying()) {
                player.pause()
                binding.button.text = "Play"
            } else {
                player.start()
                binding.button.text = "Pause"
            }
        }

        lifecycle.doOnLifecycle(onDestroy = {
            player.release()
        })
    }

    /**
     * A native method that is implemented by the 'player' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        const val TAG = "Player"

        // Used to load the 'player' library on application startup.
        init {
            System.loadLibrary("player")
        }
    }

    private fun requestMyPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            //没有授权，编写申请权限代码
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        } else {
            Log.d(TAG, "requestMyPermissions: 有写SD权限")
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            //没有授权，编写申请权限代码
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
        } else {
            Log.d(TAG, "requestMyPermissions: 有读SD权限")
        }
    }

}