package com.example.brainboost

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameActivity : AppCompatActivity(), GameColorsFragment.OnNumberSelectedListener, GameColorsFragment.OnNumberScoreListener {

    lateinit var dialog_rules : View
    lateinit var dialog_game_over : View

    var count = 0

    val fragmentManager = supportFragmentManager


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        val fragment =  GameColorsFragment()
        val fragment2 =  GameShultFragment()

        dialog_rules = layoutInflater.inflate(R.layout.dialog_rules, null)
        dialog_game_over = layoutInflater.inflate(R.layout.dialog_game_over, null)


        val textViewScore : TextView = findViewById(R.id.textView1)
        val imgHelp : ImageView = findViewById(R.id.imageView5)

        val arg: Bundle? = intent.extras

        if(arg != null){
            count = arg.getInt(MainActivity.KEY)
        }
        when(count){
            0 -> {
                textViewScore.visibility = View.GONE
                imgHelp.visibility = View.GONE
                launchFragment(fragment2)
            }
            1 -> {
                openDialogRules()
                launchFragment(fragment)
            }
            2 -> {
                textViewScore.visibility = View.GONE
                imgHelp.visibility = View.GONE
                launchFragment(fragment2)
            }
            3 -> {
                textViewScore.visibility = View.GONE
                imgHelp.visibility = View.GONE
                launchFragment(fragment2)
            }
        }
    }

    fun openDialogRules(){
        val myDialog = Dialog(this)
        myDialog.setContentView(R.layout.dialog_rules)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val textInfo : TextView = myDialog.findViewById(R.id.textViewInfo)

        val image1 : ImageView = myDialog.findViewById(R.id.imageView1)
        val image2 : ImageView = myDialog.findViewById(R.id.imageView2)
        val image3 : ImageView = myDialog.findViewById(R.id.imageView3)

        when(count){
            1 -> {
                textInfo.setText(R.string.rules_game_colors)
                image1.setImageResource(R.drawable.rules_game_color_1)
                image2.setImageResource(R.drawable.rules_game_color_2)
                image3.setImageResource(R.drawable.rules_game_color_3)
            }
        }

        val btn : Button = myDialog.findViewById(R.id.button4)
        btn.setOnClickListener{
            myDialog.dismiss()
        }

    }

    fun openDialogGameOver(score: Int){
        val myDialog = Dialog(this)
        myDialog.setContentView(dialog_game_over)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val textScore: TextView = dialog_game_over.findViewById(R.id.textViewScoreGO)
        textScore.setText("Вы набрали $score очков")

        val btn : Button = dialog_game_over.findViewById(R.id.button4)
        val btnBack : Button = dialog_game_over.findViewById(R.id.button5)
        btnBack.setOnClickListener{
            finish()
        }
        btn.setOnClickListener{
            myDialog.dismiss()
            recreate()
        }
        myDialog.setOnDismissListener {
            recreate()
        }
    }

    fun launchFragment(fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }


    fun backOnClick(view: View) {
        finish()
    }

    override fun onNumberSelected(number: Int) {
        openDialogGameOver(number)
    }

    fun rulesOnClick(view: View) {
        openDialogRules()
    }

    override fun onNumberScore(number: Int) {
        val textViewScore : TextView = findViewById(R.id.textView1)
        textViewScore.setText("Очки\n$number")
    }


}