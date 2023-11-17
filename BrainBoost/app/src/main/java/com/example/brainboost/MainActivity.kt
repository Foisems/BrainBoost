package com.example.brainboost

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.brainboost.databinding.ActivityMainBinding
import java.util.Timer

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


        val imageList = intArrayOf(
            R.drawable.human,
            R.drawable.colors,
            R.drawable.shult,
            R.drawable.passwords
        )
        val nameList = arrayOf("Люди - имена", "Цветные слова", "Таблица Шульте", "Отгадай пароль")
        for (i in imageList.indices) {
            listData = ListData(nameList[i], imageList[i])
            dataArrayList.add(ListData(nameList[i], imageList[i]))
        }
        listAdapter = ListAdapter(this@MainActivity, dataArrayList)
        binding.listview.adapter = listAdapter
        binding.listview.isClickable = true
        binding.listview.onItemClickListener = OnItemClickListener { parent, view, i, l ->

          //  val selectedItem: Int = parent.getItemAtPosition(i) as Int
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            intent.putExtra(KEY, i)
            startActivity(intent)
        }


    }




}