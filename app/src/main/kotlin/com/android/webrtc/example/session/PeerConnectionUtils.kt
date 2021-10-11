package com.android.webrtc.example.session

import android.content.Context
import org.webrtc.BuiltinAudioDecoderFactoryFactory
import org.webrtc.BuiltinAudioEncoderFactoryFactory
import org.webrtc.DefaultVideoDecoderFactory
import org.webrtc.DefaultVideoEncoderFactory
import org.webrtc.EglBase
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory

/**
 * Provides base WebRTC instances [PeerConnectionFactory] and [PeerConnection.RTCConfiguration]
 * NOTE: This class is not mandatory but simplifies work with WebRTC.
 */
class PeerConnectionUtils(
    context: Context,
    eglBaseContext: EglBase.Context
) {

    init {
        PeerConnectionFactory.InitializationOptions
            .builder(context)
            .createInitializationOptions().also { initializationOptions ->
                PeerConnectionFactory.initialize(initializationOptions)
            }
    }

    private val defaultVideoEncoderFactory = DefaultVideoEncoderFactory(eglBaseContext, false, true)
    private val defaultVideoDecoderFactory = DefaultVideoDecoderFactory(eglBaseContext)
    private val defaultAudioEncoderFactoryFactory = BuiltinAudioEncoderFactoryFactory()
    private val defaultAudioDecoderFactoryFactory = BuiltinAudioDecoderFactoryFactory()

    // Creating peer connection factory. We need it to create "PeerConnections"
    val peerConnectionFactory: PeerConnectionFactory = PeerConnectionFactory
        .builder()
        .setVideoDecoderFactory(defaultVideoDecoderFactory)
        .setVideoEncoderFactory(defaultVideoEncoderFactory)
        .setAudioDecoderFactoryFactory(defaultAudioDecoderFactoryFactory)
        .setAudioEncoderFactoryFactory(defaultAudioEncoderFactoryFactory)
        .createPeerConnectionFactory()

    // rtcConfig contains STUN and TURN servers list
    val rtcConfig = PeerConnection.RTCConfiguration(
        arrayListOf(
            // adding google's standard server
            PeerConnection.IceServer.builder("stun:stun.l.google.com:19302").createIceServer()
        )
    ).apply {
        // it's very important to use new unified sdp semantics PLAN_B is deprecated
        sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN
    }
}