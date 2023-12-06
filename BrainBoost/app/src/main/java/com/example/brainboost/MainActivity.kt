package com.example.brainboost

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.brainboost.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY = "KEY"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ListAdapter
    private lateinit var listData: ListData
    var dataArrayList = ArrayList<ListData?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scores = loadScore(this)

        val headerView = binding.navigationView.getHeaderView(0)
        val tvScore : TextView = headerView.findViewById(R.id.score)
        tvScore.text = scores.toString()

        binding.openMenu.setOnClickListener{
            binding.drawer.openDrawer(GravityCompat.START)

        }


        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (velocityX > 0) {
                    binding.drawer.openDrawer(GravityCompat.START)
                }
                return super.onFling(e1, e2, velocityX, velocityY)}}



        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item2 ->{
                    val intent = Intent(this@MainActivity, ShopActivity::class.java)
                    startActivity(intent)
                }
                R.id.item3 ->{
                    val intent = Intent(this@MainActivity, InfoActivity::class.java)
                    intent.putExtra(KEY, 3)
                    startActivity(intent)
                }
                R.id.item4 ->{
                    val intent = Intent(this@MainActivity, InfoActivity::class.java)
                    intent.putExtra(KEY, 4)
                    startActivity(intent)
                }
            }
            true
        }
        val gestureDetector = GestureDetector(this, gestureListener)
        binding.drawer.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }


        val imageList = intArrayOf(
            R.drawable.colors,
            R.drawable.pet10,
            R.drawable.light_panel,
            R.drawable.passwords
        )
        val nameList = arrayOf(
            "Цветные слова",
            "Животные и их клички",
            "Световые панели",
            "Запомни пароль"
        )
        for (i in imageList.indices) {
            listData = ListData(nameList[i], imageList[i])
            dataArrayList.add(ListData(nameList[i], imageList[i]))
        }
        listAdapter = ListAdapter(this@MainActivity, dataArrayList)
        binding.listview.adapter = listAdapter
        binding.listview.isClickable = true
    }
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
}