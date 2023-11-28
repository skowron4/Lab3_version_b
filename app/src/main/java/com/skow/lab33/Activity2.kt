package com.skow.lab33

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme1)
        val intent = intent
        val imageResourceId = intent.getIntExtra("imageResourceId", R.drawable.nothing)
        val textToShow = intent.getStringExtra("textToShow")
        val style = intent.getIntExtra("themeResourceId", R.style.AppTheme1)

        setTheme(R.style.AppTheme1)
        setContentView(R.layout.activity2)

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageResource(imageResourceId)

        val textView: TextView = findViewById(R.id.textView)
        textView.text = textToShow

        val button: Button = findViewById(R.id.buttonBack)
        button.setOnClickListener {
            finish()
        }
    }
}