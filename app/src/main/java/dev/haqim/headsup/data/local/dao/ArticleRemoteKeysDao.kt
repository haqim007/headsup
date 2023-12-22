package dev.haqim.headsup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.haqim.headsup.data.local.entity.ArticleRemoteKeys

@Dao
interface ArticleRemoteKeysDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey:ArticleRemoteKeys)

    @Query("SELECT * FROM article_remote_keys where title = :title")
    suspend fun getRemoteKeyByTitle(title: String): ArticleRemoteKeys?

    @Query("DELETE FROM article_remote_keys")
    suspend fun clearRemoteKeys()
}