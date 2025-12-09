package com.jiangdg.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jiangdg.demo.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSettings()
    }

    private fun setupSettings() {
        // Resolution spinner
        binding.spinnerResolution.setSelection(2) // Default to 1920x1080

        // Frame rate spinner
        binding.spinnerFrameRate.setSelection(1) // Default to 30 FPS

        // Change storage location button
        binding.btnChangeStorage.setOnClickListener {
            // TODO: Open directory picker
        }

        // Auto save switch
        binding.switchAutoSave.setOnCheckedChangeListener { _, isChecked ->
            // TODO: Save preference
        }

        // Notifications switch
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            // TODO: Save preference
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
