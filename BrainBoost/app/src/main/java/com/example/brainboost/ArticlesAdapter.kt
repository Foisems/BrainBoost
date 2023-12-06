package com.example.brainboost

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader
import android.content.SharedPreferences

class ArticlesAdapter(context: Context, private val articles: List<Article>) : ArrayAdapter<Article>(context, 0, articles) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("ItemState", Context.MODE_PRIVATE)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val article = articles[position]
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item_shop, parent, false)

        // Отобразить заголовок статьи
        view.findViewById<TextView>(R.id.listName).text = article.title

        val itemLayout = view?.findViewById<LinearLayout>(R.id.llqq)
        val textСost = itemLayout?.findViewById<TextView>(R.id.listInfo)

        article.isUnlocked = loadItemState(context, article)


        if (article.isUnlocked) {
            itemLayout?.setOnClickListener{
                openArticle(article)
            }
            textСost?.text = "Приобретено"
            view.alpha = 1.0f
        } else {
            view.alpha = 0.5f
            itemLayout?.setOnClickListener{
                val points = loadScore(context) // Ваши накопленные очки, полученные из внутреннего хранилища

                if (points >= 1000) { // Проверяем, достаточно ли очков для покупки статьи
                    // Вычитаем стоимость статьи из очков пользователя
                    val updatedPoints = points - 1000

                    saveScore(updatedPoints, context) // Обновляем очки во внутреннем хранилище
                    // Обновляем состояние статьи
                    article.isUnlocked = true

                    openArticle(article)
                    view.alpha = 1.0f
                    Toast.makeText(context, "Покупка совершилась", Toast.LENGTH_SHORT).show()
                } else
                {
                    // Пользователь не имеет достаточно очков для покупки статьи
                    Toast.makeText(context, "У вас недостаточно очков", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // Заблокировать/разблокировать текст статьи в зависимости от флага
        return view
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
    fun saveScore(score: Int, context: Context) {
        try {
            val fileOutputStream = context.openFileOutput("score.txt", Context.MODE_PRIVATE)
            fileOutputStream.write(score.toString().toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openArticle(article: Article) {
        // Save the updated state of the item in SharedPreferences
        saveItemState(article)

        // Open the activity with the article text
        val intent = Intent(context, ExerciseActivity::class.java).apply {
            putExtra("articleTitle", article.title)
            putExtra("articleContent", article.text)
        }
        context.startActivity(intent)
    }
    private fun saveItemState(article: Article) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(article.title, article.isUnlocked)
        editor.apply()
    }
    fun loadItemState(context: Context, article: Article): Boolean {
        return sharedPreferences.getBoolean(article.title, false)
    }
}
