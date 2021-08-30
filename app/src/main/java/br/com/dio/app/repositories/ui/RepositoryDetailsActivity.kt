package br.com.dio.app.repositories.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.createDialog
import br.com.dio.app.repositories.core.createProgressDialog
import br.com.dio.app.repositories.databinding.ActivityRepositoryDetailsBinding
import br.com.dio.app.repositories.presentation.RepoViewModel
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryDetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRepositoryDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<RepoViewModel>()
    private val dialog by lazy { createProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()

        val ownerName = intent.getStringExtra(OWNER_NAME)
        val repositoryName = intent.getStringExtra(REPOSITORY_NAME)

        if (ownerName != null && repositoryName != null) {
            viewModel.getRepo(ownerName, repositoryName)
        }



        viewModel.repo.observe(this) {
            when (it) {
                is RepoViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is RepoViewModel.State.Success -> {
                    Glide.with(this).load(it.repo.owner.avatarURL).into(binding.ivOwner)
                    binding.tvRepoName.text = it.repo.name
                    binding.tvOwner.text = it.repo.owner.login
                    binding.tvOwner.setOnClickListener { view ->
                        startActivity(
                            WebViewActivity.getIntent(
                                this,
                                it.repo.owner.htmlUrl ?: ""
                            )
                        )
                    }
                    binding.tvRepoDescription.text = it.repo.description


                    binding.tvStar.text = resources.getQuantityString(
                        R.plurals.number_of_stars,
                        it.repo.stargazersCount.toInt(), it.repo.stargazersCount.toInt()
                    )
                    binding.tvRepoForks.text = resources.getQuantityString(
                        R.plurals.number_of_forks,
                        it.repo.forksCount.toInt(), it.repo.forksCount.toInt()
                    )

                    binding.tvIssueCount.text = it.repo.openIssuesCount.toString()
                    binding.tvWatchersCount.text = it.repo.watchers.toString()
                    binding.tvLicenseName.text = it.repo.license?.spdxId
                    binding.tvSeeOnGithub.setOnClickListener { view ->
                        startActivity(
                            WebViewActivity.getIntent(
                                this,
                                it.repo.htmlURL
                            )
                        )
                    }
                }
            }
        }

/*        */
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val REPOSITORY_NAME = "repo"
        private const val OWNER_NAME = "owner"

        fun getIntent(context: Context, ownerName: String, repositoryName: String): Intent {
            return Intent(context, RepositoryDetailsActivity::class.java).apply {
                putExtra(REPOSITORY_NAME, repositoryName)
                putExtra(OWNER_NAME, ownerName)
            }
        }
    }
}