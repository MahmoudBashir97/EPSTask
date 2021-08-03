package com.mahmoudbashir.epstask.pojo

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
data class DataModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val imgUri:String,
    val title:String,
    val description:String
) {
}