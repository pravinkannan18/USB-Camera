package com.jiangdg.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangdg.demo.databinding.FragmentVideoManagementBinding

class VideoManagementFragment : Fragment() {
    private var _binding: FragmentVideoManagementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFilterButtons()
        loadVideos()
    }

    private fun setupRecyclerView() {
        binding.rvVideos.layoutManager = LinearLayoutManager(requireContext())
        // TODO: Setup adapter when video data is available
    }

    private fun setupFilterButtons() {
        binding.btnFilterAll.setOnClickListener {
            // Filter all videos
            loadVideos()
        }

        binding.btnFilterToday.setOnClickListener {
            // Filter today's videos
            loadTodayVideos()
        }

        binding.btnFilterWeek.setOnClickListener {
            // Filter this week's videos
            loadWeekVideos()
        }
    }

    private fun loadVideos() {
        // TODO: Load all videos from storage
        // For now, show empty state
        showEmptyState()
    }

    private fun loadTodayVideos() {
        // TODO: Filter videos from today
        showEmptyState()
    }

    private fun loadWeekVideos() {
        // TODO: Filter videos from this week
        showEmptyState()
    }

    private fun showEmptyState() {
        binding.emptyState.visibility = View.VISIBLE
        binding.rvVideos.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
