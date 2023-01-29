package com.phalgundixit.githubtrending.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.phalgundixit.githubtrending.adapter.RepoDetailsAdapter
import com.phalgundixit.githubtrending.databinding.ActivityContributorDetialBinding
import com.phalgundixit.githubtrending.viewModels.ReposViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ContributorDetailActivity: AppCompatActivity() {

    private lateinit var viewModel: ReposViewModel
    private lateinit var binding: ActivityContributorDetialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContributorDetialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ReposViewModel::class.java]

        viewModel.getRepoDetailsListLiveData().observe(this) {
            binding.apply {
                binding.rvContributorsRepo.apply {
                    setHasFixedSize(true)
                    itemAnimator = null
                    this.adapter = RepoDetailsAdapter(it){
                        val intent = Intent(this@ContributorDetailActivity,RepoDetailsActivity::class.java)
                        intent.putExtra(TrendingRepoSearchActivity.DETAILS_DATA,it)
                        startActivity(intent)
                    }
                    postponeEnterTransition()
                    viewTreeObserver.addOnPreDrawListener {
                        startPostponedEnterTransition()
                        true
                    }
                }
            }
        }

        intent?.let {
            if (it.hasExtra(RepoDetailsActivity.CONTRIBUTOR_REPO_URL) && it.getStringExtra(RepoDetailsActivity.CONTRIBUTOR_REPO_URL) != null) {
                val repoUrl = it.getStringExtra(RepoDetailsActivity.CONTRIBUTOR_REPO_URL)

                lifecycleScope.launch(Dispatchers.IO) {
                    repoUrl?.let { it1 -> viewModel.getRepoDetails(it1) }
                }
            }
        }


    }
}