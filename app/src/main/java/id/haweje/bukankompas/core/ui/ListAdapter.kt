package id.haweje.bukankompas.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.haweje.bukankompas.core.data.source.local.entity.HeadlinesEntity
import id.haweje.bukankompas.databinding.ItemNewsCardBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>(){

    private var onItemClickNewsCallback : OnItemClickNewsCallback? = null

    fun setOnItemClick(onItemClickNewsCallback: OnItemClickNewsCallback){
        this.onItemClickNewsCallback = onItemClickNewsCallback
    }

    inner class ListViewHolder(private val binding: ItemNewsCardBinding) : RecyclerView.ViewHolder(binding.root) {
        internal fun bind(news : HeadlinesEntity){
            binding.root.setOnClickListener { onItemClickNewsCallback?.onItemClickedNews(news) }
            binding.titleNewsId.text = news.title
            binding.titleDescriptionId.text = news.description
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .centerCrop()
                .into(binding.imageNewsId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ListViewHolder {
        val itemNewsCardBinding = ItemNewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemNewsCardBinding)
    }

    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        val news = listNews[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = listNews.size

    private val listNews = ArrayList<HeadlinesEntity>()

    fun setNews(news: List<HeadlinesEntity>){
        listNews.clear()
        listNews.addAll(news)
        notifyDataSetChanged()
        Log.d("response", listNews.toString())

    }


}

interface OnItemClickNewsCallback {
    fun onItemClickedNews(news: HeadlinesEntity)
}

