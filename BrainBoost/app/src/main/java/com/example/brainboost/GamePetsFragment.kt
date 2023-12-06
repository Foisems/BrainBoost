package com.example.brainboost

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

class GamePetsFragment : Fragment() {

    lateinit var imageViewRight: ImageView
    lateinit var imageViewHeart1: ImageView
    lateinit var imageViewHeart2: ImageView
    lateinit var imageViewHeart3: ImageView

    lateinit var img1: ImageView
    lateinit var img2: ImageView

    var countIncorrect = 0
    var score = 0

    lateinit var btnName1 : Button
    lateinit var btnName2 : Button
    lateinit var btnName3 : Button
    lateinit var btnName4 : Button

    lateinit var btnNamePaste1 : Button
    lateinit var btnNamePaste2 : Button

    lateinit var nameArray : Array<String>
    lateinit var imageNumArray : Array<Int>
    lateinit var randomList : List<Int>
    lateinit var nameArray4 : Array<String>
    lateinit var randomNameArray4 : Array<String>
    lateinit var imageArray : Array<ImageView>

    var btnNum = 0

    var textName = ""

    var countPush = 0

    var countPushName = 0

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
        val view = inflater.inflate(R.layout.fragment_game_pets, container, false)

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

        img1 = view.findViewById(R.id.imageView6)
        img2 = view.findViewById(R.id.imageView7)

        btnName1 = view.findViewById(R.id.btnName1)
        btnName2 = view.findViewById(R.id.btnName2)
        btnName3 = view.findViewById(R.id.btnName3)
        btnName4 = view.findViewById(R.id.btnName4)

        btnNamePaste1 = view.findViewById(R.id.btnNamePaste1)
        btnNamePaste2 = view.findViewById(R.id.btnNamePaste2)

        nameArray = arrayOf("Тиша", "Дымок", "Тaйсон", "Джек", "Боня", "Каспер", "Буч", "Лаки", "Феликс", "Симба", "Кайто", "Бусинкa", "Зевс")

        val buttonClickListener = View.OnClickListener { view ->
            var b1: Button? = null
            if (view is Button) {
                b1 = view
            }

            when (b1?.id) {
                R.id.btnName1 -> {
                    btnNum = 1
                    setBtnColor()
                    b1.setBackgroundResource(R.color.green)
                    textName = b1.text.toString()
                }

                R.id.btnName2 -> {
                    btnNum = 2
                    setBtnColor()
                    b1.setBackgroundResource(R.color.green)
                    textName = b1.text.toString()
                }

                R.id.btnName3 -> {
                    btnNum = 3
                    setBtnColor()
                    b1.setBackgroundResource(R.color.green)
                    textName = b1.text.toString()
                }

                R.id.btnName4 -> {
                    btnNum = 4
                    setBtnColor()
                    b1.setBackgroundResource(R.color.green)
                    textName = b1.text.toString()
                }

                R.id.btnNamePaste1 -> {
                    if(countPushName != 1 && btnNum != 0){
                        countPushName = 1
                        setNamePaste(b1)
                    }
                    if(countPush == 2){
                        countPushName = 0
                        isCorrectNamePet()
                    }
                }
                R.id.btnNamePaste2 -> {
                    if(countPushName != 2 && btnNum != 0){
                        countPushName = 2
                        setNamePaste(b1)
                    }
                    if(countPush == 2){
                        countPushName = 0
                        isCorrectNamePet()
                    }
                }
            }
        }

        btnName1.setOnClickListener(buttonClickListener)
        btnName2.setOnClickListener(buttonClickListener)
        btnName3.setOnClickListener(buttonClickListener)
        btnName4.setOnClickListener(buttonClickListener)

        btnNamePaste1.setOnClickListener(buttonClickListener)
        btnNamePaste2.setOnClickListener(buttonClickListener)

        setRandNameImage()

        return view
    }

    fun isCorrectNamePet(){
        countPush = 0
        btnName1.isClickable = false
        btnName2.isClickable = false
        btnName3.isClickable = false
        btnName4.isClickable = false

        if(nameArray4[0] == btnNamePaste1.text
            && nameArray4[1] == btnNamePaste2.text){
            imageViewRight.setImageResource(R.drawable.correct)
            score += 10
            interactionListener?.onNumberScore(score)
            Handler().postDelayed({
                setRandNameImage()
            }, 600)
        }
        else {
            imageViewRight.setImageResource(R.drawable.incorrect)
            countIncorrect++
            if (countIncorrect < 3) {
                when (countIncorrect) {
                    1 -> imageViewHeart1.setImageResource(R.drawable.heart_down)
                    2 -> imageViewHeart2.setImageResource(R.drawable.heart_down)
                }
                Handler().postDelayed({
                    setRandNameImage()
                }, 600)
            }
            else {
                imageViewHeart3.setImageResource(R.drawable.heart_down)
                interactionListener?.onNumberSelected(score)
            }
        }
        imageViewRight.animate().alpha(1F)
        Handler().postDelayed({
            imageViewRight.animate().alpha(0F)
        }, 600)
    }

    fun setBtnColor(){
        if(!btnName1.isClickable){
            btnName1.setBackgroundResource(R.color.light_gray)
            btnName2.setBackgroundResource(R.color.yellow)
            btnName3.setBackgroundResource(R.color.yellow)
            btnName4.setBackgroundResource(R.color.yellow)
        }
        else if(!btnName2.isClickable){
            btnName1.setBackgroundResource(R.color.yellow)
            btnName2.setBackgroundResource(R.color.light_gray)
            btnName3.setBackgroundResource(R.color.yellow)
            btnName4.setBackgroundResource(R.color.yellow)
        }
        else if(!btnName3.isClickable){
            btnName1.setBackgroundResource(R.color.yellow)
            btnName2.setBackgroundResource(R.color.yellow)
            btnName3.setBackgroundResource(R.color.light_gray)
            btnName4.setBackgroundResource(R.color.yellow)
        }
        else if(!btnName4.isClickable){
            btnName1.setBackgroundResource(R.color.yellow)
            btnName2.setBackgroundResource(R.color.yellow)
            btnName3.setBackgroundResource(R.color.yellow)
            btnName4.setBackgroundResource(R.color.light_gray)
        }
        else{
            btnName1.setBackgroundResource(R.color.yellow)
            btnName2.setBackgroundResource(R.color.yellow)
            btnName3.setBackgroundResource(R.color.yellow)
            btnName4.setBackgroundResource(R.color.yellow)
        }
    }

    fun setNamePaste(btnNamePaste : Button){
        if (countPush != 2){
            countPush++
            if(btnNum != 0){
                btnNamePaste.setBackgroundResource(R.color.yellow)
                btnNamePaste.text = textName
                textName = ""
                when(btnNum){
                    1 -> {
                        btnName1.setBackgroundResource(R.color.light_gray)
                        btnName1.text = textName
                        btnName1.isClickable = false
                    }
                    2 -> {
                        btnName2.setBackgroundResource(R.color.light_gray)
                        btnName2.text = textName
                        btnName2.isClickable = false
                    }
                    3 -> {
                        btnName3.setBackgroundResource(R.color.light_gray)
                        btnName3.text = textName
                        btnName3.isClickable = false
                    }
                    4 -> {
                        btnName4.setBackgroundResource(R.color.light_gray)
                        btnName4.text = textName
                        btnName4.isClickable = false
                    }
                }
            }
            btnNum = 0
        }
    }



    fun setRandNameImage(){
        randomList = (0..9).shuffled()
        imageNumArray = randomList.take(2).toTypedArray()
        imageArray = arrayOf(img1, img2)

        for(i in 0 until 2){
            when (imageNumArray[i]){
                0 -> imageArray[i].setBackgroundResource(R.drawable.pet1)
                1 -> imageArray[i].setBackgroundResource(R.drawable.pet2)
                2 -> imageArray[i].setBackgroundResource(R.drawable.pet3)
                3 -> imageArray[i].setBackgroundResource(R.drawable.pet4)
                4 -> imageArray[i].setBackgroundResource(R.drawable.pet5)
                5 -> imageArray[i].setBackgroundResource(R.drawable.pet6)
                6 -> imageArray[i].setBackgroundResource(R.drawable.pet7)
                7 -> imageArray[i].setBackgroundResource(R.drawable.pet8)
                8 -> imageArray[i].setBackgroundResource(R.drawable.pet9)
                9 -> imageArray[i].setBackgroundResource(R.drawable.pet10)
            }
        }

        nameArray.shuffle()
        nameArray4 = nameArray.take(4).toTypedArray()

        btnNamePaste1.setBackgroundResource(R.color.yellow)
        btnNamePaste1.text = nameArray4[0]
        btnNamePaste1.isClickable = false

        btnNamePaste2.setBackgroundResource(R.color.yellow)
        btnNamePaste2.text = nameArray4[1]
        btnNamePaste2.isClickable = false

        btnName1.setBackgroundResource(R.color.light_gray)
        btnName1.setText("")
        btnName1.isClickable = false
        btnName2.setBackgroundResource(R.color.light_gray)
        btnName2.setText("")
        btnName2.isClickable = false
        btnName3.setBackgroundResource(R.color.light_gray)
        btnName3.setText("")
        btnName3.isClickable = false
        btnName4.setBackgroundResource(R.color.light_gray)
        btnName4.setText("")
        btnName4.isClickable = false

        Handler().postDelayed({
            btnNamePaste1.setBackgroundResource(R.color.light_gray)
            btnNamePaste1.setText("")
            btnNamePaste1.isClickable = true
            btnNamePaste2.setBackgroundResource(R.color.light_gray)
            btnNamePaste2.setText("")
            btnNamePaste2.isClickable = true

            randomNameArray4 =  nameArray4.clone().apply { shuffle() }

            btnName1.setBackgroundResource(R.color.yellow)
            btnName1.text = randomNameArray4[0]
            btnName1.isClickable = true
            btnName2.setBackgroundResource(R.color.yellow)
            btnName2.text = randomNameArray4[1]
            btnName2.isClickable = true
            btnName3.setBackgroundResource(R.color.yellow)
            btnName3.text = randomNameArray4[2]
            btnName3.isClickable = true
            btnName4.setBackgroundResource(R.color.yellow)
            btnName4.text = randomNameArray4[3]
            btnName4.isClickable = true
        }, 3000)
    }
}