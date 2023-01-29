package com.phalgundixit.githubtrending.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phalgundixit.githubtrending.data.model.Repo
import com.phalgundixit.githubtrending.databinding.ItemContributorsBinding

class RepoDetailsAdapter(private var itemsList: ArrayList<Repo>, val contributorOnClick : (Repo) -> Unit) :
    RecyclerView.Adapter<RepoDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemContributorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemsList[position].let { repo ->
            holder.bind(
                createOnClickListener(repo, position),
                repo
            )
        }
    }

    private fun createOnClickListener(repo: Repo, position: Int): View.OnClickListener {
        return View.OnClickListener {
            contributorOnClick(itemsList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class ViewHolder(val binding: ItemContributorsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, repo: Repo) {

            binding.apply {

                Glide.with(itemView)
                    .load(repo.owner?.avatarUrl)
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(ivUserAvatar)

                tvUsername.text = repo.name
                ViewCompat.setTransitionName(this.ivUserAvatar, "avatar_${repo.id}")
                root.setOnClickListener(listener)
            }
        }
    }
}