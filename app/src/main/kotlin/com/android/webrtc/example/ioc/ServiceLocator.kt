package com.android.webrtc.example.ioc

import android.content.Context
import com.android.webrtc.example.session.PeerConnectionUtils
import com.android.webrtc.example.session.WebRtcSessionManager
import com.android.webrtc.example.signaling.SignalingClient
import java.lang.ref.WeakReference
import org.webrtc.EglBase

object ServiceLocator {
    private lateinit var context: WeakReference<Context>
    val signalingClient = SignalingClient()

    private val peerConnectionUtils by lazy {
        PeerConnectionUtils(
            context.get() ?: error("context has not been initialized"),
            eglBaseContext
        )
    }

    val webRtcSessionManager by lazy {
        WebRtcSessionManager(
            context.get() ?: error("context has not been initialized"),
            signalingClient,
            peerConnectionUtils
        )
    }

    val eglBaseContext = EglBase.create().eglBaseContext


    fun initWithContext(context: Context) {
        this.context = WeakReference<Context>(context)
    }
}