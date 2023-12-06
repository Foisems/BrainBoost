package com.example.brainboost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val articleTitle = intent.getStringExtra("articleTitle")
        val articleContent = intent.getStringExtra("articleContent")

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val contentTextView = findViewById<TextView>(R.id.contentTextView)

        titleTextView.text = articleTitle
        contentTextView.text = articleContent
    }

    fun btnClickBack(view: View) {
        val intent = Intent(this, ShopActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ShopActivity::class.java)
        intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}