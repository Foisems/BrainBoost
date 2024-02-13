package com.example.brainboost

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

class GameActivity : AppCompatActivity(), FragmentInteractionListener  {

    val random = Random.Default

    var isOpenDialogRules: Boolean? = null

    lateinit var gameColorsFragment : GameColorsFragment
    lateinit var gameLightPanelFragment : GameLightPanelFragment
    lateinit var gamePasswordFragment : GamePasswordFragment
    lateinit var gamePetsFragment : GamePetsFragment

    lateinit var dialog_rules : View
    lateinit var dialog_game_over : View
    lateinit var dialog_game_control_point : View

    var scoresNum = 0

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameColorsFragment =  GameColorsFragment()
        gameLightPanelFragment =  GameLightPanelFragment()
        gamePetsFragment =  GamePetsFragment()
        gamePasswordFragment =  GamePasswordFragment()

        val gameActivityLayout : ConstraintLayout = findViewById(R.id.viewconstraint)

        dialog_rules = layoutInflater.inflate(R.layout.dialog_rules, null)
        dialog_game_over = layoutInflater.inflate(R.layout.dialog_game_over, null)
        dialog_game_control_point = layoutInflater.inflate(R.layout.dialog_game_control_point, null)

        scoresNum = loadScore(this)

        val arg: Bundle? = intent.extras

        if(arg != null){
            count = arg.getInt(MainActivity.KEY)
        }
        when(count){
            0 -> {
                openDialogRules()
                launchFragment(gameColorsFragment)
            }
            1 -> {
                openDialogRules()
                gameActivityLayout.setBackgroundResource(R.drawable.back_4)
            }
            2 -> {
                gameActivityLayout.setBackgroundResource(R.drawable.back_3)
                openDialogRules()
            }
            3 -> {
                openDialogRules()
                launchFragment(gamePasswordFragment)
            }
        }
    }
    override fun onBackPressed() {
    }


    fun openDialogRules(){
        val myDialog = Dialog(this)
        myDialog.setContentView(R.layout.dialog_rules)

        isOpenDialogRules = true

        myDialog.setCancelable(false)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val textInfo : TextView = myDialog.findViewById(R.id.textViewInfo)
        val textInfoScore : TextView = myDialog.findViewById(R.id.textViewInfoScore)

        val image1 : ImageView = myDialog.findViewById(R.id.imageView1)
        val image2 : ImageView = myDialog.findViewById(R.id.imageView2)
        val image3 : ImageView = myDialog.findViewById(R.id.open_menu)

        textInfoScore.setText(R.string.info_score_100)
        when(count){
            0 -> {
                textInfo.setText(R.string.rules_game_colors)
                image1.setImageResource(R.drawable.rules_game_color_1)
                image2.setImageResource(R.drawable.rules_game_color_2)
                image3.setImageResource(R.drawable.rules_game_color_3)
            }
            1 -> {
                textInfo.setText(R.string.rules_game_pets)
                image1.setImageResource(R.drawable.rules_game_pets_1)
                image2.setImageResource(R.drawable.rules_game_pets_2)
                image3.setImageResource(R.drawable.rules_game_pets_3)
            }
            2 -> {
                textInfo.setText(R.string.rules_game_light_panel)
                image1.setImageResource(R.drawable.rules_game_light_panel_1)
                image2.setImageResource(R.drawable.rules_game_light_panel_2)
                image3.setImageResource(R.drawable.rules_game_light_panel_3)
            }
            3 -> {
                textInfo.setText(R.string.rules_game_password)
                image1.setImageResource(R.drawable.rules_game_password_1)
                image2.setImageResource(R.drawable.rules_game_password_2)
                image3.setImageResource(R.drawable.rules_game_password_3)
            }
        }

        val btn : Button = myDialog.findViewById(R.id.button4)
        btn.setOnClickListener{
            myDialog.dismiss()
            isOpenDialogRules = false
            if (count == 1){
                launchFragment(gamePetsFragment)
            }
            if (count == 2){
                launchFragment(gameLightPanelFragment)
            }
            if (count == 3){
                openDialogPassword(this)
            }
        }
    }

    fun openDialogGameOver(score: Int){
        val myDialog = Dialog(this)
        myDialog.setContentView(dialog_game_over)

        myDialog.setCancelable(false)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val textScore: TextView = dialog_game_over.findViewById(R.id.textViewScoreGO)
        textScore.setText("Вы набрали $score очков\nКонтрольная сумма ${(score / 100).toInt() * 100} очков")

        saveScore( (score / 100).toInt() * 100,this)

        val btn : Button = dialog_game_over.findViewById(R.id.button4)
        val btnBack : Button = dialog_game_over.findViewById(R.id.button5)
        btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
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


    fun openDialogPassword(context: Context){
        val myDialog = Dialog(context)
        myDialog.setContentView(R.layout.dialog_game_password)

        myDialog.setCancelable(false)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val textPassword : TextView = myDialog.findViewById(R.id.textViewPasswordNum)
        val randomPassword = random.nextInt(10, 100000000)

        textPassword.text = randomPassword.toString()
        myDialog.show()

        val handler = Handler()
        handler.postDelayed({
            myDialog.dismiss()
        }, 5000)

        DataHolder.randomPassword = randomPassword.toString()
    }

    var multiplier = 1.0
    fun openDialogControlPoint(score : Int){
        val myDialog = Dialog(this)
        myDialog.setContentView(R.layout.dialog_game_control_point)

        myDialog.setCancelable(false)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val tVInfoControlPoint : TextView = myDialog.findViewById(R.id.textViewInfoControlPoint)
        val textViewControlPoint : TextView = myDialog.findViewById(R.id.textViewControlPoint)

        val btnContinue : Button = myDialog.findViewById(R.id.btnContinue)
        val btnFinish : Button = myDialog.findViewById(R.id.btnFinish)

        if(score == 1000){
            textViewControlPoint.text = "Вы выиграли!"
            tVInfoControlPoint.text = "Ура, вы выиграли!\n Вы набрали $score очков!\nВаш множитель: ${multiplier.format(1)}\n\n" +
                    "К вашему счёту прибавится ${(score * multiplier).toInt()} очков!"
            multiplier = 1.0
            btnContinue.visibility = View.GONE

        }
        else{
            multiplier += 0.1
            tVInfoControlPoint.text = "Ура! Вы набрали $score очков!\nВаш множитель: ${multiplier.format(1)}\n\n" +
                    "При завершении игры, к вашему\nсчёту прибавится ${(score * multiplier).toInt()} очков\n\n" +
                    "При продолжении - несгораемая сумма составит $score очков\n\n" +
                    "Хотите продолжить?"
        }

        btnContinue.setOnClickListener{
            myDialog.dismiss()
            when(count){
                0 -> restartFragment(gameColorsFragment)
                1 -> restartFragment(gamePetsFragment)
                2 -> restartFragment(gameLightPanelFragment)
                3 -> {
                    restartFragment(gamePasswordFragment)
                    openDialogPassword(this)
                }
            }
        }
        btnFinish.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            saveScore( (score * multiplier).toInt(), this)
            multiplier = 1.0
        }
    }
    fun Double.format(digits: Int) = "%.${digits}f".format(this)

    fun loadScore(context: Context): Int {
        var score = 0
        try {
            val fileInputStream = context.openFileInput("score.txt")
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            fileInputStream.close()
            score = Integer.parseInt(stringBuilder.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return score
    }
    fun saveScore(score: Int, context: Context) {
        try {
            val fileOutputStream = context.openFileOutput("score.txt", Context.MODE_PRIVATE)
            val sscore = scoresNum + score
            fileOutputStream.write(sscore.toString().toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openDialogBack(score: Int){
        val myDialog = Dialog(this)
        myDialog.setContentView(R.layout.dialog_game_back)

        myDialog.setCancelable(false)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val textViewInfoBack : TextView = myDialog.findViewById(R.id.textViewInfoBack)

        val btnContinue : Button = myDialog.findViewById(R.id.btnContinue)
        val btnFinish : Button = myDialog.findViewById(R.id.btnFinish)

        textViewInfoBack.text = "При выходе на ваш счёт добавится\n${(score / 100).toInt() * 100} очков"

        btnContinue.setOnClickListener{
            myDialog.dismiss()
            when(count){
                0 -> restartFragment(gameColorsFragment)
                1 -> restartFragment(gamePetsFragment)
                2 -> restartFragment(gameLightPanelFragment)
                3 -> {
                    restartFragment(gamePasswordFragment)
                    openDialogPassword(this)
                }
            }
        }
        btnFinish.setOnClickListener{
            saveScore((score / 100).toInt() * 100, this)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
    fun restartFragment(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction()
            .detach(fragment)
            .commit()
        this.supportFragmentManager.beginTransaction()
            .attach(fragment)
            .commit()
    }
    fun launchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    var score : Int = 0

    fun backOnClick(view: View) {
        openDialogBack(score)
    }

    override fun onNumberSelected(number: Int) {
        openDialogGameOver(number)
    }

    fun rulesOnClick(view: View) {
        openDialogRules()
    }

    override fun onNumberScore(number: Int) {
        score = number
        if(number % 100 == 0){
            openDialogControlPoint(number)
        }
        val textViewScore : TextView = findViewById(R.id.textView1)
        textViewScore.setText("Очки\n$number")
    }
}