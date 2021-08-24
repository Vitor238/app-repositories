package br.com.dio.app.repositories.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.app.repositories.databinding.ActivityRepositoryBinding

class RepositoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRepositoryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getStringExtra(REPO_URL)?.let { url ->
            binding.wvRepo.loadUrl(url)
            binding.wvRepo.settings.javaScriptEnabled = true
            setupToolbar(url)
        }
    }

    private fun setupToolbar(title: String) {
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.wvRepo.canGoBack()) {
            binding.wvRepo.goBack()
        } else {
            super.onBackPressed()
            finish()
        }
    }

    companion object {
        private const val REPO_URL = "repoUrl"
        fun getIntent(context: Context, repoUrl: String): Intent {
            return Intent(context, RepositoryActivity::class.java)
                .apply {
                    putExtra(REPO_URL, repoUrl)
                }
        }
    }
}