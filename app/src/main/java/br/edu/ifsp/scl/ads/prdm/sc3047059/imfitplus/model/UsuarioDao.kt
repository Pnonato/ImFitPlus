package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: Usuario): Long

    @Query("SELECT * FROM usuarios")
    suspend fun getUsers(): List<Usuario>

    @Query("SELECT * FROM usuarios WHERE id = :userId")
    suspend fun getUsersById(userId: Int): List<Usuario>

}
