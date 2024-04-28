package com.example.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.player.databinding.ActivityMainBinding
import com.example.player.ext.doOnLifecycle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        player = Player(this)

        player.initResId(R.string.video_url)

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
        // Used to load the 'player' library on application startup.
        init {
            System.loadLibrary("player")
        }
    }
}