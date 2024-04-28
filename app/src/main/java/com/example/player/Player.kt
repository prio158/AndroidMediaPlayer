package com.example.player

import android.content.Context
import android.media.MediaPlayer

class Player(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun initResId(resId: Int) {
        mediaPlayer = MediaPlayer.create(context, resId)
    }

    fun isPlaying() = mediaPlayer?.isPlaying ?: false

    fun start() {
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }


}