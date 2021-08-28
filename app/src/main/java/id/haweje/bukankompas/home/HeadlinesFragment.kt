package id.haweje.bukankompas.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.bukankompas.R
import id.haweje.bukankompas.core.data.source.local.entity.LocalNewsEntity
import id.haweje.bukankompas.core.ui.ListAdapter
import id.haweje.bukankompas.core.ui.OnItemClickNewsCallback
import id.haweje.bukankompas.core.utils.ViewModelFactory
import id.haweje.bukankompas.core.vo.Status
import id.haweje.bukankompas.databinding.FragmentHomeBinding
import id.haweje.bukankompas.detail.DetailActivity

class HeadlinesFragment : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var listAdapter: ListAdapter
    private val binding get() = _binding

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        listAdapter = ListAdapter()
        listAdapter.notifyDataSetChanged()
        listAdapter.setOnItemClick(object : OnItemClickNewsCallback{
            override fun onItemClickedNews(news: LocalNewsEntity) {
                Intent(activity, DetailActivity::class.java).also{
                    it.putExtra(DetailActivity.EXTRA_DATA, news)
                    startActivity(it)
                }
            }
        })

        binding?.apply {
            rvNews.layoutManager = LinearLayoutManager(view.context)
            rvNews.adapter = listAdapter
            rvNews.setHasFixedSize(true)
        }

        val factory = ViewModelFactory.getInstance(requireContext())
        newsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
        newsViewModel.getIndonesiaNews().observe(viewLifecycleOwner, {
            if (it != null){
                when(it.status){
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS ->{
                        showLoading(false)
                        it.data?.let { news -> listAdapter.setNews(news) }
                        listAdapter.notifyDataSetChanged()
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