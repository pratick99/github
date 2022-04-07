package com.pratik.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pratik.github.databinding.ActivityMainBinding
import com.pratik.github.ui.commitDetails.CommitDetailFragment
import com.pratik.github.ui.commitlist.CommitListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }

    override fun onResume() {
        super.onResume()
        val commitListFragment = CommitListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, commitListFragment)
            .addToBackStack(null)
            .commit()
    }

    fun navigateToCommitDetailsFragment(sha: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, CommitDetailFragment.newInstance(sha))
            .addToBackStack(null)
            .commit()
    }
}
