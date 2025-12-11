package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val userName: String,
    val imc: Double,
    val imcCategory: String,
    val tmb: Double,
    val idealWeight: Double,
    val ingestaoAgua: Double,
    val timestamp: Long = System.currentTimeMillis()
)
