package com.example.brainboost


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val arg : Bundle? = intent.extras

        val tvInfo : TextView = findViewById(R.id.textViewInfo)
        val tvInfoAct : TextView = findViewById(R.id.textViewInfoAct)

        if (arg != null){
            val infoCount = arg.getInt(MainActivity.KEY)
            when (infoCount){
                3 ->{
                    tvInfoAct.setText(R.string.help)
                    tvInfo.setText(R.string.help_info)
                    tvInfo.textSize = 18F
                }
                4 ->{
                    tvInfoAct.setText(R.string.about_program)
                    tvInfo.setText(R.string.about_program_info)
                }
            }
        }
    }

    fun btnClickBack(view: View) {
        finish()
    }
}