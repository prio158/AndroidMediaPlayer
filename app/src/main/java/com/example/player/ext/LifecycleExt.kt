package com.example.player.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

val defaultLifecycleStub: () -> Unit = {}

/**
 * 在对应生命周期执行对应的代码块
 */
fun Lifecycle.doOnLifecycle(
    onCreate: () -> Unit = defaultLifecycleStub,
    onStart: () -> Unit = defaultLifecycleStub,
    onResume: () -> Unit = defaultLifecycleStub,
    onPause: () -> Unit = defaultLifecycleStub,
    onStop: () -> Unit = defaultLifecycleStub,
    onDestroy: () -> Unit = defaultLifecycleStub
): LifecycleObserver = object : FullLifecycleObserverAdapter() {
    override fun onCreate(owner: LifecycleOwner) = onCreate()
    override fun onStart(owner: LifecycleOwner) = onStart()
    override fun onResume(owner: LifecycleOwner) = onResume()
    override fun onPause(owner: LifecycleOwner) = onPause()
    override fun onStop(owner: LifecycleOwner) = onStop()
    override fun onDestroy(owner: LifecycleOwner) = onDestroy()
}.apply(this::addObserver)


abstract class FullLifecycleObserverAdapter : LifecycleEventObserver, FullLifecycleObserver {
    final override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate(source)
            Lifecycle.Event.ON_START -> onStart(source)
            Lifecycle.Event.ON_RESUME -> onResume(source)
            Lifecycle.Event.ON_PAUSE -> onPause(source)
            Lifecycle.Event.ON_STOP -> onStop(source)
            Lifecycle.Event.ON_DESTROY -> onDestroy(source)
            Lifecycle.Event.ON_ANY -> Unit
        }
    }
}

interface FullLifecycleObserver : LifecycleObserver {
    fun onCreate(owner: LifecycleOwner) {}
    fun onStart(owner: LifecycleOwner) {}
    fun onResume(owner: LifecycleOwner) {}
    fun onPause(owner: LifecycleOwner) {}
    fun onStop(owner: LifecycleOwner) {}
    fun onDestroy(owner: LifecycleOwner) {}
}