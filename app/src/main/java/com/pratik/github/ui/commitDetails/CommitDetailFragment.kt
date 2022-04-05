package com.pratik.github.ui.commitDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pratik.github.data.remote.dto.Root
import com.pratik.github.databinding.FragmentCommitDetailBinding
import com.pratik.github.ui.util.CommitViewState
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val COMMIT_SHA = "Commit_Sha"

/**
 * A simple [Fragment] subclass.
 * Use the [CommitDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommitDetailFragment : DaggerFragment() {
    private var commitSha: String = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentCommitDetailBinding
    private var viewModel: CommitDetailViewModel ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            commitSha = it.getString(COMMIT_SHA) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommitDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CommitDetailViewModel::class.java)
        viewModel?.getCommitViewStateLiveData()?.observe(viewLifecycleOwner, {
            when (it) {
                is CommitViewState.Loading -> {
                    Toast.makeText(context, "Fetching Data...please wait", Toast.LENGTH_LONG).show()
                }
                is CommitViewState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }

                is CommitViewState.Success -> {
                    assignDataToViews(it.data)
                }
            }
        })
        viewModel?.getCommitDetail(commitSha)
    }

    private fun assignDataToViews(data: Root) {
        binding.authorName.text = data.commit?.author?.name
        binding.authorEmail.text = data.commit?.author?.email
        binding.commitMessageText.text = data.commit?.message
    }

    companion object {

        @JvmStatic
        fun newInstance(commitSha: String) =
            CommitDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(COMMIT_SHA, commitSha)
                }
            }
    }
}
