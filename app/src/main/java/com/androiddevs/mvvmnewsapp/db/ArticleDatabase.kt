package com.androiddevs.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.model.Article

@Database(
    entities = [Article::class], version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        // Whenever we invoke our database
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: articleDatabase(context).also { instance = it }
        }

        private fun articleDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "ArticleDB.db"
            ).build()
    }
}