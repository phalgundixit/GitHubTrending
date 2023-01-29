package com.phalgundixit.githubtrending.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.phalgundixit.githubtrending.activities.TrendingRepoSearchActivity.Companion.DETAILS_DATA
import com.phalgundixit.githubtrending.adapter.ContributorAdapter
import com.phalgundixit.githubtrending.data.model.Repo
import com.phalgundixit.githubtrending.databinding.ActivityRepoDetailsBinding
import com.phalgundixit.githubtrending.viewModels.ReposViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepoDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ReposViewModel
    private lateinit var binding: ActivityRepoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ReposViewModel::class.java]

        viewModel.getContributorListLiveData().observe(this) {
            binding.apply {
                binding.rvContributors.apply {
                    setHasFixedSize(true)
                    itemAnimator = null
                    this.adapter = ContributorAdapter(it){
                        val intent = Intent(this@RepoDetailsActivity,ContributorDetailActivity::class.java)
                        intent.putExtra(CONTRIBUTOR_REPO_URL,it.reposUrl)
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
            if (it.hasExtra(DETAILS_DATA) && it.getParcelableExtra<Repo>(DETAILS_DATA) != null) {
                val detailsData = it.getParcelableExtra<Repo>(DETAILS_DATA)

                lifecycleScope.launch(Dispatchers.IO) {
                    detailsData?.contributorsUrl?.let { it1 -> viewModel.getContributors(it1) }
                }

                Glide.with(this)
                    .load(detailsData?.owner?.avatarUrl)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(binding.ivRepoDetailsAvatar)
                val str =
                    SpannableString((detailsData?.owner?.login ?: "") + " / " + detailsData?.name)
                str.setSpan(
                    StyleSpan(Typeface.NORMAL),
                    detailsData?.owner?.login?.length ?: 0,
                    str.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.tvRepoDetailsUsername.text = str
                binding.tvRepoDesc.text = detailsData?.description
                binding.tvRepoUrl.text = detailsData?.htmlUrl

            }
        }


    }
    companion object{
        const val CONTRIBUTOR_REPO_URL = "CONTRIBUTOR_REPO_URL"
    }
}