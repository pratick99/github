package com.pratik.github.ui.commits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pratik.github.databinding.CommitListFragmentBinding
import com.pratik.github.di.Injectable
import javax.inject.Inject

class CommitListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: CommitListFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val commitListAdapter: CommitListAdapter by lazy { CommitListAdapter() }
    private lateinit var viewModel: CommitListViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CommitListFragmentBinding.inflate(inflater, container, false)
        recyclerView = binding.commitListRecyclerview
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.adapter = commitListAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CommitListViewModel::class.java)
        viewModel.commitList.observe(viewLifecycleOwner, Observer {
            commitListAdapter.submitList(it)
        })
    }

    companion object {
        fun newInstance() = CommitListFragment()
    }

}