package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.adapters.SavedNewsAdapter
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository

class SavedNewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var savedNewsAdapter: SavedNewsAdapter
    private lateinit var rvSavedNews: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_news)

        rvSavedNews = findViewById(R.id.rv_saved_news)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProvider(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        viewModel.getSavedNews().observe(this, { articles ->
            savedNewsAdapter.differ.submitList(articles)
        })

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        savedNewsAdapter = SavedNewsAdapter()
        rvSavedNews.apply {
            adapter = savedNewsAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
    }
}