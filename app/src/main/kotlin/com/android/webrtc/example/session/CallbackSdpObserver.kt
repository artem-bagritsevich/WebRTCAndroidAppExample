package com.android.webrtc.example.session

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

/**
 * [SdpObserver] implementation with default callbacks and ability to override them
 * NOTE: This class is not mandatory but simplifies work with WebRTC.
 */
class CallbackSdpObserver(
    private val onCreate: (SessionDescription) -> Unit = {},
    private val onSuccess: () -> Unit = {},
    private val onFailure: (String?) -> Unit = {}
) : SdpObserver {

    override fun onCreateSuccess(sessionDescroption: SessionDescription?) {
        sessionDescroption ?: return
        onCreate(sessionDescroption)
    }

    override fun onSetSuccess() {
        onSuccess()
    }

    override fun onCreateFailure(message: String?) {
        onFailure(message)
    }

    override fun onSetFailure(message: String?) {
        onFailure(message)
    }
}