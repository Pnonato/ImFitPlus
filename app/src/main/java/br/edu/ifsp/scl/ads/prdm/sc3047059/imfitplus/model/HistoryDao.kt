package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert
    suspend fun insertHistory(history: History)

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    suspend fun getAllHistory(): List<History>

    @Query("SELECT * FROM history WHERE userId = :id ORDER BY timestamp DESC")
    suspend fun getHistoryByUser(id: Int): List<History>

}