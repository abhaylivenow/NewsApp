package com.example.newsapp.ui

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.model.Article
import com.example.newsapp.model.Source
import com.example.newsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.news_preview.view.*

class ReadNewsActivity : AppCompatActivity() {

    private lateinit var imageThumbnail: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var textAuthorName: TextView
    private lateinit var textFullNews: TextView
    private lateinit var textHeading: TextView
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_news)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProvider(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        imageThumbnail = findViewById(R.id.image_thumbnail)
        btnBack = findViewById(R.id.btn_back)
        textAuthorName = findViewById(R.id.text_author_name)
        textFullNews = findViewById(R.id.text_full_news)
        textHeading = findViewById(R.id.text_heading)

        btnBack.setOnClickListener {
            onBackPressed()
        }
        /*
        dataIntent.putExtra("imageUrl",article.urlToImage)
                dataIntent.putExtra("heading",article.title)
                dataIntent.putExtra("description",article.description)
                dataIntent.putExtra("time",article.publishedAt)
                dataIntent.putExtra("author",article.author)
         */
        val imageUrl = intent.getStringExtra("imageUrl")
        val heading = intent.getStringExtra("heading")
        val description = intent.getStringExtra("description")
        val content = intent.getStringExtra("content")
        val time = intent.getStringExtra("time")
        val author = intent.getStringExtra("author")

        Glide.with(this).load(imageUrl).into(imageThumbnail)
        textAuthorName.text = author
        textFullNews.text = content
        textHeading.text = heading
    }
}