package com.sallyjayz.asteroidradar.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "picture_table")
data class PictureOfDay(
    @Json(name = "media_type")
    @ColumnInfo(name = "media_type")
    val mediaType: String,
    val title: String,
    @PrimaryKey
    val url: String,
    val date: String
    ): Parcelable