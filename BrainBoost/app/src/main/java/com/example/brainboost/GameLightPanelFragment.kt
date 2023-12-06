package com.example.brainboost

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import kotlin.random.Random


class GameLightPanelFragment : Fragment() {

    val random = Random.Default

    lateinit var imageViewRight: ImageView
    lateinit var imageViewHeart1: ImageView
    lateinit var imageViewHeart2: ImageView
    lateinit var imageViewHeart3: ImageView

    lateinit var panel0 : Button
    lateinit var panel1 : Button
    lateinit var panel2 : Button
    lateinit var panel3 : Button
    lateinit var panel4 : Button
    lateinit var panel5 : Button
    lateinit var panel6 : Button
    lateinit var panel7 : Button
    lateinit var panel8 : Button
    lateinit var panel9 : Button
    lateinit var panel10 : Button
    lateinit var panel11 : Button
    lateinit var panel12 : Button
    lateinit var panel13 : Button
    lateinit var panel14 : Button
    lateinit var panel15 : Button
    lateinit var panelEnter : Button

    lateinit var gridLayout: GridLayout

    lateinit var randomList : List<Int>
    var randomNumber : Int = 0
    lateinit var randomArray : Array<Int>

    var isCorrect : Boolean = true

    var countIncorrect = 0
    var score = 0

    private var interactionListener: FragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_light_panel, container, false)

        gridLayout = view.findViewById(R.id.gridLayout)

        imageViewRight = view.findViewById(R.id.imageViewIsCorrect)
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

        panel0 = view.findViewById(R.id.panel0)
        panel1 = view.findViewById(R.id.panel1)
        panel2 = view.findViewById(R.id.panel2)
        panel3 = view.findViewById(R.id.panel3)
        panel4 = view.findViewById(R.id.panel4)
        panel5 = view.findViewById(R.id.panel5)
        panel6 = view.findViewById(R.id.panel6)
        panel7 = view.findViewById(R.id.panel7)
        panel8 = view.findViewById(R.id.panel8)
        panel9 = view.findViewById(R.id.panel9)
        panel10 = view.findViewById(R.id.panel10)
        panel11 = view.findViewById(R.id.panel11)
        panel12 = view.findViewById(R.id.panel12)
        panel13 = view.findViewById(R.id.panel13)
        panel14 = view.findViewById(R.id.panel14)
        panel15 = view.findViewById(R.id.panel15)

        panelEnter = view.findViewById(R.id.panelEnter)

        val buttonClickListener = View.OnClickListener { view ->
            var b1: Button? = null
            if (view is Button) {
                b1 = view
            }
            when (b1?.text) {
                "" -> {
                    val background = b1.background
                    val buttonBackgroundColor = (background as? ColorDrawable)?.color
                    val resourceColor = ContextCompat.getColor(requireContext(), R.color.light_gray)

                    if (buttonBackgroundColor == resourceColor) {
                        b1.setBackgroundResource(R.color.yellow)
                    } else {
                        b1.setBackgroundResource(R.color.light_gray)
                    }
                }
                "Проверить" -> {
                    b1.isClickable = false
                    isCorrectLightPanels()
                }
            }
        }

        setClickButtons(gridLayout, buttonClickListener)

        panelEnter.setOnClickListener(buttonClickListener)

        randLightPanel()

        return view
    }

    fun randLightPanel(){
        panelEnter.isClickable = false
        grayColorButtons(gridLayout)
        disableButtons(gridLayout, false)

        randomList = (0..15).shuffled()
        randomNumber = random.nextInt(3, 14)
        randomArray = randomList.take(randomNumber).toTypedArray()

        for(i in 0 until randomNumber){
            when (randomArray[i]){
                0 -> panel0.setBackgroundResource(R.color.yellow)
                1 -> panel1.setBackgroundResource(R.color.yellow)
                2 -> panel2.setBackgroundResource(R.color.yellow)
                3 -> panel3.setBackgroundResource(R.color.yellow)
                4 -> panel4.setBackgroundResource(R.color.yellow)
                5 -> panel5.setBackgroundResource(R.color.yellow)
                6 -> panel6.setBackgroundResource(R.color.yellow)
                7 -> panel7.setBackgroundResource(R.color.yellow)
                8 -> panel8.setBackgroundResource(R.color.yellow)
                9 -> panel9.setBackgroundResource(R.color.yellow)
                10 -> panel10.setBackgroundResource(R.color.yellow)
                11 -> panel11.setBackgroundResource(R.color.yellow)
                12 -> panel12.setBackgroundResource(R.color.yellow)
                13 -> panel13.setBackgroundResource(R.color.yellow)
                14 -> panel14.setBackgroundResource(R.color.yellow)
                15 -> panel15.setBackgroundResource(R.color.yellow)
            }
        }
        Handler().postDelayed({
            panelEnter.isClickable = true
            grayColorButtons(gridLayout)
            disableButtons(gridLayout, true)
        }, 5000)
    }

    fun isCorrectLightPanels(){
        for(i in 0 until randomNumber){
            when (randomArray[i]){
                0 -> isCorrect(panel0)
                1 -> isCorrect(panel1)
                2 -> isCorrect(panel2)
                3 -> isCorrect(panel3)
                4 -> isCorrect(panel4)
                5 -> isCorrect(panel5)
                6 -> isCorrect(panel6)
                7 -> isCorrect(panel7)
                8 -> isCorrect(panel8)
                9 -> isCorrect(panel9)
                10 -> isCorrect(panel10)
                11 -> isCorrect(panel11)
                12 -> isCorrect(panel12)
                13 -> isCorrect(panel13)
                14 -> isCorrect(panel14)
                15 -> isCorrect(panel15)
            }
        }

        if (isCorrect == false){
            incorrectColor()
            isCorrect = true
            imageViewRight.setImageResource(R.drawable.incorrect)
            countIncorrect++
            if(countIncorrect < 3 ) {
                when (countIncorrect) {
                    1 -> imageViewHeart1.setImageResource(R.drawable.heart_down)
                    2 -> imageViewHeart2.setImageResource(R.drawable.heart_down)
                }
                Handler().postDelayed({
                    randLightPanel()
                }, 600)
            }
            else{
                imageViewHeart3.setImageResource(R.drawable.heart_down)
                interactionListener?.onNumberSelected(score)
            }
        }
        else {
            imageViewRight.setImageResource(R.drawable.correct)
            score += 10
            interactionListener?.onNumberScore(score)
            Handler().postDelayed({
                randLightPanel()
            }, 600)
        }
        imageViewRight.animate().alpha(1F)
        Handler().postDelayed({
            imageViewRight.animate().alpha(0F)
        }, 600)


    }

    fun isCorrect(panel : Button){
        val background = panel.background
        val buttonBackgroundColor = (background as? ColorDrawable)?.color
        val resourceColor = ContextCompat.getColor(requireContext(), R.color.yellow)

        if (buttonBackgroundColor != resourceColor) {
            isCorrect = false
        }
    }

    fun incorrectColor(){
        for(i in 0 until randomNumber){
            when (randomArray[i]){
                0 -> panel0.setBackgroundResource(R.color.red)
                1 -> panel1.setBackgroundResource(R.color.red)
                2 -> panel2.setBackgroundResource(R.color.red)
                3 -> panel3.setBackgroundResource(R.color.red)
                4 -> panel4.setBackgroundResource(R.color.red)
                5 -> panel5.setBackgroundResource(R.color.red)
                6 -> panel6.setBackgroundResource(R.color.red)
                7 -> panel7.setBackgroundResource(R.color.red)
                8 -> panel8.setBackgroundResource(R.color.red)
                9 -> panel9.setBackgroundResource(R.color.red)
                10 -> panel10.setBackgroundResource(R.color.red)
                11 -> panel11.setBackgroundResource(R.color.red)
                12 -> panel12.setBackgroundResource(R.color.red)
                13 -> panel13.setBackgroundResource(R.color.red)
                14 -> panel14.setBackgroundResource(R.color.red)
                15 -> panel15.setBackgroundResource(R.color.red)
            }
        }
    }

    private fun grayColorButtons(layout: GridLayout) {
        layout.children.filterIsInstance<Button>().forEach { button ->
            button.setBackgroundResource(R.color.light_gray)
        }
    }

    private fun setClickButtons(layout: GridLayout,  buttonClickListener: View.OnClickListener) {
        layout.children.filterIsInstance<Button>().forEach { button ->
            button.setOnClickListener(buttonClickListener)
        }
    }

    private fun disableButtons(layout: GridLayout, isEnabled: Boolean) {
        layout.children.filterIsInstance<Button>().forEach { button ->
            button.isEnabled = isEnabled
            button.isClickable = isEnabled
        }
    }
}

