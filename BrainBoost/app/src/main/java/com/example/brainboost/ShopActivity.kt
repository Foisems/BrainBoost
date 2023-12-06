package com.example.brainboost

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class ShopActivity : AppCompatActivity() {
    private lateinit var articlesListView: ListView
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var articlesList: ArrayList<Article>

    private  var articlesListSave = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        articlesListSave.add(Article("Диафрагмальное дыхание", getString(R.string.ex1), false))
        articlesListSave.add(Article("Дыхание 4-7-8", getString(R.string.ex2), false))
        articlesListSave.add(Article("Задуй свечу", getString(R.string.ex3), false))
        articlesListSave.add(Article("Ровное дыхание", getString(R.string.ex4), false))
        articlesListSave.add(Article("«Свободное письмо» (фрирайтинг)", getString(R.string.ex5), false))

        saveArticlesToInternalStorage(articlesListSave)

        // Загрузка данных о статьях из внутреннего хранилища
        articlesList = loadArticlesFromInternalStorage()

        articlesListView = findViewById(R.id.listview)
        articlesAdapter = ArticlesAdapter(this, articlesList)
        articlesListView.adapter = articlesAdapter

        articlesListView.isClickable = true
    }

    private fun loadArticlesFromInternalStorage(): ArrayList<Article> {
    // Загрузка данных о статьях из внутреннего хранилища
        val sharedPreferences = getSharedPreferences("Articles", Context.MODE_PRIVATE)
        val articlesJson = sharedPreferences.getString("ArticlesList", "")
        val articlesType = object : TypeToken<ArrayList<Article>>() {}.type
        return Gson().fromJson(articlesJson, articlesType) ?: ArrayList()
    }

    private fun saveArticlesToInternalStorage(articlesList: ArrayList<Article>) {
        // Сериализация списка статей в JSON-строку
        val articlesJson = Gson().toJson(articlesList)

        // Сохранение JSON-строки во внутреннем хранилище
        val sharedPreferences = getSharedPreferences("Articles", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("ArticlesList", articlesJson)
        editor.apply()
    }

    fun btnClickBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}