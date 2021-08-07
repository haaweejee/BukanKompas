package id.haweje.bukankompas.home.technology

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.bukankompas.R
import id.haweje.bukankompas.core.data.source.local.entity.TechEntity
import id.haweje.bukankompas.core.ui.TechAdapter
import id.haweje.bukankompas.core.utils.ViewModelFactory
import id.haweje.bukankompas.core.vo.Status
import id.haweje.bukankompas.databinding.FragmentTechBinding
import id.haweje.bukankompas.detail.DetailActivity
import id.haweje.bukankompas.home.NewsViewModel


class TechFragment : Fragment(R.layout.fragment_tech) {

    private var _binding : FragmentTechBinding? = null
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var techAdapter: TechAdapter
    private val binding get() = _binding

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTechBinding.bind(view)

        techAdapter = TechAdapter()
        techAdapter.setOnItemClick(object : TechAdapter.OnItemClickNewsCallback {
            override fun onItemClickedNews(news: TechEntity) {
                Intent(activity, DetailActivity::class.java).also{
                    it.putExtra(DetailActivity.EXTRA_TITLE, news.title)
                    it.putExtra(DetailActivity.EXTRA_DESC, news.description)
                    it.putExtra(DetailActivity.EXTRA_IMAGE, news.urlToImage)
                    it.putExtra(DetailActivity.EXTRA_AUTHOR, news.author)
                    it.putExtra(DetailActivity.EXTRA_DATE, news.publishedAt)
                    it.putExtra(DetailActivity.EXTRA_CONTENT, news.content)
                    it.putExtra(DetailActivity.EXTRA_URL, news.url)
                    it.putExtra(DetailActivity.EXTRA_ID, news.id)
                    startActivity(it)
                }
            }
        })


        binding?.apply {
            rvNews.layoutManager = LinearLayoutManager(view.context)
            rvNews.adapter = techAdapter
            rvNews.setHasFixedSize(true)
        }

        showLoading(true)
        val factory = ViewModelFactory.getInstance(requireContext())
        newsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
        newsViewModel.getIndonesiaTechNews().observe(viewLifecycleOwner,{
            if (it != null){
                when(it.status){
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS ->{
                        showLoading(false)
                        it.data?.let { news -> techAdapter.setNews(news) }
                        techAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR ->{
                        showLoading(false)
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }
}