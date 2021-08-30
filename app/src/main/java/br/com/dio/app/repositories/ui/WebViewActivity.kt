package br.com.dio.app.repositories.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.app.repositories.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {
    private val binding by lazy { ActivityWebviewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getStringExtra(URL)?.let { url ->
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
        private const val URL = "url"
        fun getIntent(context: Context, url: String): Intent {
            return Intent(context, WebViewActivity::class.java)
                .apply {
                    putExtra(URL, url)
                }
        }
    }
}