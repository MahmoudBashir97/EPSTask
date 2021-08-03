package com.mahmoudbashir.epstask.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mahmoudbashir.epstask.pojo.DataModel

@Dao
interface StDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model:DataModel)

    @Query("SELECT * FROM data_table ORDER BY id ASC")
    fun getAllDataStored():LiveData<List<DataModel>>

    @Delete
    fun deleteItem(model: DataModel)
}