package dev.haqim.headsup.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.haqim.headsup.data.local.dao.ArticleDao
import dev.haqim.headsup.data.local.dao.ArticleRemoteKeysDao
import dev.haqim.headsup.data.local.dao.SavedArticleDao
import dev.haqim.headsup.data.local.entity.ArticleEntity
import dev.haqim.headsup.data.local.entity.ArticleRemoteKeys
import dev.haqim.headsup.data.local.entity.SavedArticleEntity

@Database(
    entities = [ArticleRemoteKeys::class, ArticleEntity::class, SavedArticleEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun remoteKeysDao(): ArticleRemoteKeysDao
    abstract fun articleDao(): ArticleDao
    abstract fun savedArticleDao(): SavedArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "headsup.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { 
                        INSTANCE = it
                    }
            }
        }
    }
}