package id.haweje.bukankompas.detail

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import id.haweje.bukankompas.R
import id.haweje.bukankompas.core.data.source.local.entity.LocalNewsEntity
import id.haweje.bukankompas.core.utils.ViewModelFactory
import id.haweje.bukankompas.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "EXTRA DATA"
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel: DetailNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[DetailNewsViewModel::class.java]


        val detailNews = intent.getParcelableExtra<LocalNewsEntity>(EXTRA_DATA)
        getData(detailNews)
    }

    private fun getData(detailNews : LocalNewsEntity?){
        detailNews.let {
            binding.webView.apply {
                webViewClient = WebViewClient()
                detailNews?.url?.let { news -> loadUrl(news) }
            }

            var statusBookmark = detailNews?.bookmarked
            if (statusBookmark != null) {
                setStatusBookmark(statusBookmark)
            }

            binding.btnBookmark.setOnClickListener {
                statusBookmark = !statusBookmark!!
                if (detailNews != null) {
                    viewModel.setBookmark(detailNews, statusBookmark!!)
                }
                setStatusBookmark(statusBookmark!!)
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setStatusBookmark(statusBookmark: Boolean) {
        if (statusBookmark) {
            binding.btnBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmarked))
        } else {
            binding.btnBookmark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_bookmark))
        }
    }

}