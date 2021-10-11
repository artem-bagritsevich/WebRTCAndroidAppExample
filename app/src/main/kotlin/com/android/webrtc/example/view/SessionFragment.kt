package com.android.webrtc.example.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.webrtc.example.databinding.FragmentSecondBinding
import com.android.webrtc.example.ioc.ServiceLocator
import kotlinx.coroutines.flow.collect

/**
 * On this fragment we will see local and remote video.
 */
class SessionFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val webRtcSessionManager = ServiceLocator.webRtcSessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initializing local view
        with(binding.localVideo) {
            init(ServiceLocator.eglBaseContext, null)
            setEnableHardwareScaler(true)
            setMirror(true)
        }

        // initializing remote view
        with(binding.remoteView) {
            init(ServiceLocator.eglBaseContext, null)
            setEnableHardwareScaler(true)
        }

        // subscribing for the local video track event
        // to receive it and add local video view to sink
        lifecycleScope.launchWhenStarted {
            webRtcSessionManager.localVideoSinkFlow.collect { localVideoTrack ->
                localVideoTrack.addSink(binding.localVideo)
            }
        }

        // subscribing for the remote video track event
        // to receive it and add remote video view to sink
        lifecycleScope.launchWhenStarted {
            webRtcSessionManager.remoteVideoSinkFlow.collect { remoteVideoTrack ->
                remoteVideoTrack.addSink(binding.remoteView)
            }
        }

        // notifying session manager that we are ready to receive video
        webRtcSessionManager.onSessionScreenReady()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}