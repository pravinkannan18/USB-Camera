package com.jiangdg.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jiangdg.demo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cardStartRecording.setOnClickListener {
            // Navigate to Live Streaming fragment
            (activity as? MainActivity)?.navigateToLiveStreaming()
        }

        binding.cardViewRecordings.setOnClickListener {
            // Navigate to Video Management fragment
            (activity as? MainActivity)?.navigateToVideoManagement()
        }

        binding.cardSettings.setOnClickListener {
            // Navigate to Settings fragment
            (activity as? MainActivity)?.navigateToSettings()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
