package id.haweje.bukankompas.detail

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.haweje.bukankompas.R
import id.haweje.bukankompas.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_TITLE = "EXTRA TITLE"
        const val EXTRA_DESC = "EXTRA DESC"
        const val EXTRA_IMAGE = "EXTRA IMAGE"
        const val EXTRA_AUTHOR = "EXTRA AUTHOR"
        const val EXTRA_DATE = "EXTRA DATE"
        const val EXTRA_URL = "EXTRA URL"
        const val EXTRA_CONTENT = "EXTRA CONTENT"
        const val EXTRA_ID = "EXTRA ID"
    }

    private lateinit var binding : ActivityDetailBinding
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        getData()
    }

    private fun getData(){
        val title = intent.getStringExtra(EXTRA_TITLE)
        binding.tvTitleNewsId.text = title

        val desc = intent.getStringExtra(EXTRA_DESC)
        binding.tvDescNewsId.text = desc

        val author = intent.getStringExtra(EXTRA_AUTHOR)
        val date = intent.getStringExtra(EXTRA_DATE)
        binding.tvAuthorsAndDateNewsId.text = "Oleh $author - $date"

        val content = intent.getStringExtra(EXTRA_CONTENT)
        binding.tvContentNewsId.text = content

        val image = intent.getStringExtra(EXTRA_IMAGE)
        Glide.with(this)
            .load(image)
            .centerCrop()
            .into(binding.imgNewsId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }
}