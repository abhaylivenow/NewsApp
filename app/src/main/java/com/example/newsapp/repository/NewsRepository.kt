package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitBuilder
import com.example.newsapp.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitBuilder.api.getNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitBuilder.api.searchNews(searchQuery, pageNumber)
}