package com.example.brainboost

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.random.Random


class GamePasswordFragment : Fragment() {


    var password = "0"

    val random = Random.Default
    lateinit var textPassword : TextView

    lateinit var imageViewRight : ImageView
    lateinit var imageViewHeart1 : ImageView
    lateinit var imageViewHeart2 : ImageView
    lateinit var imageViewHeart3 : ImageView

    var countIncorrect = 0
    var score = 0


    private var interactionListener: FragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Проверяем, что контекст активности реализует интерфейс
        if (context is FragmentInteractionListener) {
            interactionListener = context
        } else {
            throw ClassCastException("$context must implement FragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_password, container, false)

        val numEnter : Button = view.findViewById(R.id.numEnter)
        textPassword = view.findViewById(R.id.textViewNumber)

        imageViewRight = view.findViewById(R.id.imageView)
        imageViewHeart1 = view.findViewById(R.id.imageViewHeart1)
        imageViewHeart2 = view.findViewById(R.id.imageViewHeart2)
        imageViewHeart3 = view.findViewById(R.id.imageViewHeart3)

        when(countIncorrect){
            1 -> imageViewHeart1.setImageResource(R.drawable.heart_down)
            2 -> {
                imageViewHeart1.setImageResource(R.drawable.heart_down)
                imageViewHeart2.setImageResource(R.drawable.heart_down)
            }
            3 -> imageViewHeart3.setImageResource(R.drawable.heart_down)
        }

        val btn0 = view.findViewById<Button>(R.id.num0)
        val btn1 = view.findViewById<Button>(R.id.num1)
        val btn2 = view.findViewById<Button>(R.id.num2)
        val btn3 = view.findViewById<Button>(R.id.num3)
        val btn4 = view.findViewById<Button>(R.id.num4)
        val btn5 = view.findViewById<Button>(R.id.num5)
        val btn6 = view.findViewById<Button>(R.id.num6)
        val btn7 = view.findViewById<Button>(R.id.num7)
        val btn8 = view.findViewById<Button>(R.id.num8)
        val btn9 = view.findViewById<Button>(R.id.num9)

        val numBack = view.findViewById<Button>(R.id.numBack)

        val buttonClickListener = View.OnClickListener { view ->
            var b1: Button? = null
            if (view is Button) {
                b1 = view
            }
            when (b1?.text) {

                "0" -> {
                    textPassword.text = textPassword.text.toString() + "0"
                }
                "1" -> {
                    textPassword.text = textPassword.text.toString() + "1"
                }
                "2" -> {
                    textPassword.text = textPassword.text.toString() + "2"
                }
                "3" -> {
                    textPassword.text = textPassword.text.toString() + "3"
                }
                "4" -> {
                    textPassword.text = textPassword.text.toString() + "4"
                }
                "5" -> {
                    textPassword.text = textPassword.text.toString() + "5"
                }
                "6" -> {
                    textPassword.text = textPassword.text.toString() + "6"
                }
                "7" -> {
                    textPassword.text = textPassword.text.toString() + "7"
                }
                "8" -> {
                    textPassword.text = textPassword.text.toString() + "8"
                }
                "9" -> {
                    textPassword.text = textPassword.text.toString() + "9"
                }
                "Ввести" -> {
                    isCorrectPassword(b1)
                }
                "ᐊ" ->{
                    if (textPassword.text != "")
                        textPassword.text = textPassword.text.substring(0, textPassword.text.toString().length - 1)
                    else textPassword.text = textPassword.text
                }
            }
        }

        btn0.setOnClickListener(buttonClickListener)
        btn1.setOnClickListener(buttonClickListener)
        btn2.setOnClickListener(buttonClickListener)
        btn3.setOnClickListener(buttonClickListener)
        btn4.setOnClickListener(buttonClickListener)
        btn5.setOnClickListener(buttonClickListener)
        btn6.setOnClickListener(buttonClickListener)
        btn7.setOnClickListener(buttonClickListener)
        btn8.setOnClickListener(buttonClickListener)
        btn9.setOnClickListener(buttonClickListener)

        numEnter.setOnClickListener(buttonClickListener)
        numBack.setOnClickListener(buttonClickListener)


        return view
    }

    fun isCorrectPassword(btn: Button){
        btn.isClickable = false
        password =  DataHolder.randomPassword
        if(password == textPassword.text){
            imageViewRight.setImageResource(R.drawable.correct)
            score += 10
            interactionListener?.onNumberScore(score)
            Handler().postDelayed({
                textPassword.text = ""
                if(score % 100 != 0){
                    openDialogPassword(requireContext())
                }
                btn.isClickable = true
            }, 600)
        }
        else {
            imageViewRight.setImageResource(R.drawable.incorrect)
            countIncorrect++
            if(countIncorrect < 3 ) {
                when (countIncorrect) {
                    1 -> imageViewHeart1.setImageResource(R.drawable.heart_down)
                    2 -> imageViewHeart2.setImageResource(R.drawable.heart_down)
                }
                Handler().postDelayed({
                    textPassword.text = ""
                    openDialogPassword(requireContext())
                    btn.isClickable = true
                }, 600)
            }
            else{
                imageViewHeart3.setImageResource(R.drawable.heart_down)
                interactionListener?.onNumberSelected(score)
            }
        }
        imageViewRight.animate().alpha(1F)
        Handler().postDelayed({
            imageViewRight.animate().alpha(0F)
        }, 600)
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

        DataHolder.randomPassword = randomPassword.toString()

        val handler = Handler()
        handler.postDelayed({
            myDialog.dismiss()
        }, 5000)
    }

}