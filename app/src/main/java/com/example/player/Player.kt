package com.example.player

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.SurfaceHolder


class Player(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    var isStop = false
        private set

    //    //①raw下的资源：
//    MediaPlayer.create(this, R.raw.test)
//
//    //②本地文件路径：
//    mediaPlayer.setDataSource("/sdcard/test.mp3")
//
//    ③网络URL文件：
//    mediaPlayer.setDataSource("http://www.xxx.com/music/test.mp3")
    fun initPlayer(resId: Int) {
        mediaPlayer = MediaPlayer.create(context, resId)


//    另一种初始化方法
//    Uri myUri = ....; // initialize Uri here
//    mediaPlayer = new MediaPlayer();
//    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//    mediaPlayer.setDataSource(getApplicationContext(), myUri);
//    mediaPlayer.prepare();
//    mediaPlayer.start();

//    val url = "http://........" // your URL here
//    mediaPlayer = MediaPlayer()
//    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//    mediaPlayer.setDataSource(url)
//    mediaPlayer.prepare() // might take long! (for buffering, etc)
//    mediaPlayer.start()

    }

    fun setMediaParam(holder: SurfaceHolder) {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA) //用于音频/视频播放
            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE) //用于视频播放
            .build()
        mediaPlayer?.setAudioAttributes(audioAttributes)
        mediaPlayer?.setDisplay(holder)
    }

    fun isPlaying() = mediaPlayer?.isPlaying ?: false

    fun prepare() {
        mediaPlayer?.prepare()
        isStop = false
    }

    fun start() {
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun stop() {
        mediaPlayer?.stop()
        isStop = true
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun reset() {
        mediaPlayer?.reset()
        isStop = false
    }


}