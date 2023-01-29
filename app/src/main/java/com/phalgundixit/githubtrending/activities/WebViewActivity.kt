package com.phalgundixit.githubtrending.activities


import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.phalgundixit.githubtrending.R
import com.phalgundixit.githubtrending.activities.RepoDetailsActivity.Companion.WEB_VIEW_URL
import com.phalgundixit.githubtrending.databinding.ActivityWebViewBinding


class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        intent?.let {
            if (it.hasExtra(WEB_VIEW_URL) && it.getStringExtra(WEB_VIEW_URL) != null) {
                val repoUrl = it.getStringExtra(WEB_VIEW_URL)
                startWebView(repoUrl?:"")
            }
        }


    }
    private fun startWebView(url: String) {
        binding.webView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.loadWithOverviewMode = true
        val progressDialog = ProgressDialog(this@WebViewActivity)
        progressDialog.setMessage("Loading...")
        progressDialog.show()
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {

            }
        }
        binding.webView.loadUrl(url)
    }


}