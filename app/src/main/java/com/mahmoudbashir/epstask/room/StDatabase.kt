package com.mahmoudbashir.epstask.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahmoudbashir.epstask.pojo.DataModel

@Database(entities = [DataModel::class],version = 1,exportSchema = false)
abstract class StDatabase :RoomDatabase(){
    abstract fun getDao():StDao

    companion object{
        @Volatile
        private var instance:StDatabase?=null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                StDatabase::class.java,
                "St_List_db"
            ).build()
    }
}