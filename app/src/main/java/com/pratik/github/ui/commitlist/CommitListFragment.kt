package com.pratik.github.ui.commitlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pratik.github.MainActivity
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.databinding.CommitListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommitListFragment : Fragment(), OnItemClickListener {

    private var _binding: CommitListFragmentBinding ?= null
    private val binding: CommitListFragmentBinding
        get() = _binding!!

    private var commitListAdapter: CommitListAdapter ?= null

    private val viewModel: CommitListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommitListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commitListAdapter = CommitListAdapter(this)
        binding.commitListRecyclerview.adapter = commitListAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCommits().collectLatest { commits ->
                commitListAdapter?.submitData(commits)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        commitListAdapter = null
        _binding = null
    }

    companion object {
        fun newInstance() = CommitListFragment()
    }

    override fun onItemClicked(root: Root) {
        (activity as MainActivity).navigateToCommitDetailsFragment(root.sha)
    }
}
