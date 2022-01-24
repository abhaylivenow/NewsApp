package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    /*
    Api key: b4fde7ed09e64358b6e0c00da8a39b72
     */
    private lateinit var imageBtnSaved: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageBtnSaved = findViewById(R.id.image_button_saved)
        imageBtnSaved.setOnClickListener {
            startActivity(
                Intent(this@MainActivity,SavedNews::class.java)
            )
        }
    }
}