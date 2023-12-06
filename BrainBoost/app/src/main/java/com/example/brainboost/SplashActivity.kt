package com.example.brainboost

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.brainboost.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        (AnimatorInflater.loadAnimator(this,R.drawable.anim_brain) as ObjectAnimator).apply {
            target = binding.imageView3
            start()
        }
        (AnimatorInflater.loadAnimator(this,R.drawable.anim_text_brainboost) as ObjectAnimator).apply {
            target = binding.textView
            start()
        }

        binding.imageView2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.anim_show))
        binding.textView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.anim_show))

        scheduleSplashScreen()
    }
    private fun scheduleSplashScreen() {
        val imageView = findViewById<ImageView>(R.id.imageView2)
        imageView.setBackgroundResource(R.drawable.anim_splash)
        val animation = imageView.background as AnimatedVectorDrawable
        animation.start()

        val splashScreenDuration: Long = 2500
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenDuration)
    }

}