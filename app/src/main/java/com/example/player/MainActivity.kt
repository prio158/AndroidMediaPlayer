package com.example.player

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.player.databinding.ActivityMainBinding
import com.example.player.ext.doOnLifecycle


class MainActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        requestMyPermissions()

        binding.surfaceView.holder.addCallback(this)

        player = Player(this)

        binding.button.setOnClickListener {
            if (player.isStop) {
                player.prepare()
            }
            if (!player.isPlaying()) {
                player.start()
            }
        }

        binding.buttonPause.setOnClickListener {
            if (player.isPlaying())
                player.pause()
        }

        binding.buttonStop.setOnClickListener {
            player.stop()
        }

        lifecycle.doOnLifecycle(onDestroy = {
            if (player.isPlaying())
                player.stop()
            player.release()

        })
    }

    companion object {
        const val TAG = "Player"

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

    override fun surfaceCreated(holder: SurfaceHolder) {
        player.initPlayer(R.raw.demo)
        player.setMediaParam(holder)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

}