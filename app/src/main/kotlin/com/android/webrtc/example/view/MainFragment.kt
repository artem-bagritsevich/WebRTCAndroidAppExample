package com.android.webrtc.example.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.webrtc.example.R
import com.android.webrtc.example.databinding.FragmentFirstBinding
import com.android.webrtc.example.ioc.ServiceLocator
import com.android.webrtc.example.signaling.WebRTCSessionState
import kotlinx.coroutines.flow.collect

/**
 * Fragment which subscribes for the session state and handles it.
 * From this fragment user can start [SessionFragment] if session is in [WebRTCSessionState.Ready]
 */
class MainFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    // getting session manager just to init it via lazy block
    private val webRtcSessionManager = ServiceLocator.webRtcSessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startSessionButton.setOnClickListener {
            findNavController().navigate(R.id.action_start_session)
        }
        lifecycleScope.launchWhenStarted {
            ServiceLocator.signalingClient.sessionStateFlow.collect { state ->
                when (state) {
                    WebRTCSessionState.Offline -> handleOfflineState()
                    WebRTCSessionState.Impossible -> handleImpossibleState()
                    WebRTCSessionState.Ready -> handleReadyState()
                    WebRTCSessionState.Creating -> handleCreatingState()
                    WebRTCSessionState.Active -> handleActiveState()
                }
            }
        }
        // NOTE: Don't do like this in production! Always check for the result
        // Here we are just assuming that user will allow access
        requireActivity().requestPermissions(arrayOf(Manifest.permission.CAMERA), 0)
    }

    // state handling is just changing message in textview and text/visibility of the button
    private fun handleOfflineState() {
        binding.sessionInfoTextView.setText(R.string.session_offline)
        binding.startSessionButton.apply {
            setText(R.string.button_start_session)
            isEnabled = false
        }
    }

    private fun handleImpossibleState() {
        binding.sessionInfoTextView.setText(R.string.session_impossible)
        binding.startSessionButton.apply {
            setText(R.string.button_start_session)
            isEnabled = false
        }
    }

    private fun handleReadyState() {
        binding.sessionInfoTextView.setText(R.string.session_ready)
        binding.startSessionButton.apply {
            setText(R.string.button_start_session)
            isEnabled = true
        }
    }

    private fun handleCreatingState() {
        binding.sessionInfoTextView.setText(R.string.session_creating)
        binding.startSessionButton.apply {
            setText(R.string.button_join_session)
            isEnabled = true
        }
    }

    private fun handleActiveState() {
        binding.sessionInfoTextView.setText(R.string.session_active)
        binding.startSessionButton.apply {
            setText(R.string.button_join_session)
            isEnabled = false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}