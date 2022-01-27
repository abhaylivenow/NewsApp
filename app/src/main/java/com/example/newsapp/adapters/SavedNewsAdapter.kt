package com.example.newsapp.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.ReadNewsActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.news_preview.view.*
import kotlinx.android.synthetic.main.news_preview.view.text_date
import kotlinx.android.synthetic.main.news_preview.view.text_heading
import kotlinx.android.synthetic.main.saved_news_preview.view.*

class SavedNewsAdapter: RecyclerView.Adapter<SavedNewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.saved_news_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        Log.i("here",article.publishedAt)
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(image_saved_thumbnail)
            text_saved_heading.text = article.title
//            text_short_description.text = article.description
            text_saved_date.text = article.publishedAt
//            text_saved_author.text = article.author
//            setOnClickListener {
//                onItemClickListener?.let { it(article) }
//                val dataIntent = Intent(
//                    context,
//                    ReadNewsActivity::class.java
//                )
//                dataIntent.putExtra("imageUrl",article.urlToImage)
//                dataIntent.putExtra("heading",article.title)
//                dataIntent.putExtra("description",article.description)
//                dataIntent.putExtra("content",article.content)
//                dataIntent.putExtra("time",article.publishedAt)
//                dataIntent.putExtra("author",article.author)
//
//                context.startActivity(dataIntent)
//            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}