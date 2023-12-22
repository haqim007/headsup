package dev.haqim.headsup.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * To store information of fetched latest page
 *
 * @property id
 * @property prevKey
 * @property nextKey
 * @constructor Create empty Remote keys
 */
@Entity(tableName = "article_remote_keys")
data class ArticleRemoteKeys(
    @PrimaryKey
    val title: String,
    val prevKey: Int?,
    val nextKey: Int?
)