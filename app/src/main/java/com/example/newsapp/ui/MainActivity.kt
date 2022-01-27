package com.example.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.utils.Resource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var edtSearch: EditText
    private lateinit var rvNews: RecyclerView
    private lateinit var ImageBtnSaved: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProvider(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        progressBar = findViewById(R.id.progressBar)
        rvNews = findViewById(R.id.rv_news)
        edtSearch = findViewById(R.id.search)
        ImageBtnSaved = findViewById(R.id.image_button_saved)

        ImageBtnSaved.setOnClickListener {
            startActivity(
                Intent(this,SavedNewsActivity::class.java)
            )
        }

        setupRecyclerView()

        var job: Job? = null
        edtSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500)
                it?.let {
                    if(it.toString().isNotEmpty()) {
                        viewModel.searchNews(it.toString())
                    }
                }
            }
        }

        viewModel.breakingNews.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(applicationContext,"Error occurred!",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.searchNews.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(applicationContext,"Error occurred!",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(viewModel)
        rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }
    }
}