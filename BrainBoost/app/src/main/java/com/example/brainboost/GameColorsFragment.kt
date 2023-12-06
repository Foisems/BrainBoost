package com.example.brainboost

import android.os.Bundle
import android.os.Handler
import android.content.Context
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlin.random.Random

class GameColorsFragment : Fragment() {

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

    val random = Random.Default

    lateinit var imageViewRight : ImageView
    lateinit var imageViewHeart1 : ImageView
    lateinit var imageViewHeart2 : ImageView
    lateinit var imageViewHeart3 : ImageView
    lateinit var textColor : TextView

    lateinit var btn1 : Button
    lateinit var btn2 : Button
    lateinit var btn3 : Button
    lateinit var btn4 : Button
    lateinit var image : ImageView

    var countIncorrect = 0
    var score = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_game_colors, container, false)

        textColor = view.findViewById(R.id.textViewColor)
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

        btn1 = view.findViewById(R.id.button)
        btn2 = view.findViewById(R.id.button1)
        btn3  = view.findViewById(R.id.button2)
        btn4 = view.findViewById(R.id.button3)

        image = view.findViewById(R.id.imageView)

        randTextColor()
        randButtonColor()

        btn1.setOnClickListener{
            answer(btn1)
        }
        btn2.setOnClickListener{
            answer(btn2)
        }
        btn3.setOnClickListener{
            answer(btn3)
        }
        btn4.setOnClickListener{
            answer(btn4)
        }
        return view
    }

    fun answer(btn : Button){
        image.animate().alpha(1F)
        isCorrectColor(btn)
        randTextColor()
        randButtonColor()

         Handler().postDelayed({
            image.animate().alpha(0F)
        }, 600)
    }

    fun isCorrectColor(btn: Button){
        var color = textColor.currentTextColor
        var textColor : String = ""

        when(color){
            ContextCompat.getColor(requireContext(), R.color.red) ->
                textColor = getString(R.string.game_color_red)
            ContextCompat.getColor(requireContext(),R.color.green) ->
                textColor = getString(R.string.game_color_green)
            ContextCompat.getColor(requireContext(),R.color.textColorYellow) ->
                textColor = getString(R.string.game_color_yellow)
            ContextCompat.getColor(requireContext(),R.color.blue) ->
                textColor = getString(R.string.game_color_blue)
        }
        if (textColor == btn.text){
            imageViewRight.setImageResource(R.drawable.correct)
            score += 10
            interactionListener?.onNumberScore(score)
        }
        else {
            imageViewRight.setImageResource(R.drawable.incorrect)
            countIncorrect++
            when(countIncorrect){
                1 -> imageViewHeart1.setImageResource(R.drawable.heart_down)
                2 -> imageViewHeart2.setImageResource(R.drawable.heart_down)
                3 -> imageViewHeart3.setImageResource(R.drawable.heart_down)
            }
        }
        if(countIncorrect == 3){
            interactionListener?.onNumberSelected(score)
        }
    }

    fun randTextColor(){
        val randomNumberColorText = random.nextInt(1, 5)
        val randomNumberText = random.nextInt(1, 5)

        when(randomNumberColorText){
            1 -> {
                textColor.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
            2 -> {
                textColor.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
            3 -> {
                textColor.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorYellow))
            }
            4 -> {
                textColor.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            }
        }
        when(randomNumberText){
            1 -> {
                textColor.setText(R.string.game_color_blue)
            }
            2 -> {
                textColor.setText(R.string.game_color_yellow)
            }
            3 -> {
                textColor.setText(R.string.game_color_red)
            }
            4 -> {
                textColor.setText(R.string.game_color_green)
            }
        }
    }

    fun randButtonColor(){
        val arrayNumColor = mutableListOf(1, 2, 3, 4)
        arrayNumColor.shuffle()
        val resultArrayNumColor = arrayNumColor.take(4)
        arrayNumColor.shuffle()
        val resultArrayNumColor1 = arrayNumColor.take(4)

        when(resultArrayNumColor[0]){
            1 -> {
                btn1.setText(R.string.game_color_blue)
            }
            2 -> {
                btn1.setText(R.string.game_color_green)
            }
            3 -> {
                btn1.setText(R.string.game_color_red)
            }
            4 -> {
                btn1.setText(R.string.game_color_yellow)
            }
        }
        when(resultArrayNumColor[1]){
            1 -> {
                btn2.setText(R.string.game_color_blue)
            }
            2 -> {
                btn2.setText(R.string.game_color_green)
            }
            3 -> {
                btn2.setText(R.string.game_color_red)
            }
            4 -> {
                btn2.setText(R.string.game_color_yellow)
            }
        }
        when(resultArrayNumColor[2]){
            1 -> btn3.setText(R.string.game_color_blue)
            2 -> btn3.setText(R.string.game_color_green)
            3 -> btn3.setText(R.string.game_color_red)
            4 -> btn3.setText(R.string.game_color_yellow)
        }
        when(resultArrayNumColor[3]){
            1 -> btn4.setText(R.string.game_color_blue)
            2 -> btn4.setText(R.string.game_color_green)
            3 -> btn4.setText(R.string.game_color_red)
            4 -> btn4.setText(R.string.game_color_yellow)
        }
        when(resultArrayNumColor1[0]){
            1 -> {
                btn1.setBackgroundResource(R.drawable.button_game_color_blue)
                btn1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            2 -> {
                btn1.setBackgroundResource(R.drawable.button_game_color_green)
                btn1.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
            }
            3 -> {
                btn1.setBackgroundResource(R.drawable.button_game_color_red)
                btn1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            4 -> {
                btn1.setBackgroundResource(R.drawable.button_game_color_yellow)
                btn1.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
            }
        }
        when(resultArrayNumColor1[1]){
            1 -> {
                btn2.setBackgroundResource(R.drawable.button_game_color_blue)
                btn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            2 -> {
                btn2.setBackgroundResource(R.drawable.button_game_color_green)
                btn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
            }
            3 -> {
                btn2.setBackgroundResource(R.drawable.button_game_color_red)
                btn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            4 -> {
                btn2.setBackgroundResource(R.drawable.button_game_color_yellow)
                btn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
            }
        }
        when(resultArrayNumColor1[2]){
            1 -> {
                btn3.setBackgroundResource(R.drawable.button_game_color_blue)
                btn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            2 -> {
                btn3.setBackgroundResource(R.drawable.button_game_color_green)
                btn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
            }
            3 -> {
                btn3.setBackgroundResource(R.drawable.button_game_color_red)
                btn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            4 -> {
                btn3.setBackgroundResource(R.drawable.button_game_color_yellow)
                btn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
            }
        }
        when(resultArrayNumColor1[3]){
            1 -> {
                btn4.setBackgroundResource(R.drawable.button_game_color_blue)
                btn4.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            2 -> {
                btn4.setBackgroundResource(R.drawable.button_game_color_green)
                btn4.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
            }
            3 -> {
                btn4.setBackgroundResource(R.drawable.button_game_color_red)
                btn4.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            4 -> {
                btn4.setBackgroundResource(R.drawable.button_game_color_yellow)
                btn4.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorDark))
            }
        }
    }


}