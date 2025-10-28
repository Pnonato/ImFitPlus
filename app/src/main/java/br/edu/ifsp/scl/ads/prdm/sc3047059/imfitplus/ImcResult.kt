package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImcResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_result)

        val imcResultTv = findViewById<TextView>(R.id.imc_result_tv)
        val imcCategoryTv = findViewById<TextView>(R.id.imc_category_tv)
        val calculateCaloriesBt = findViewById<Button>(R.id.calculate_calories_bt)
        val backBt = findViewById<Button>(R.id.back_bt)

        val name = intent.getStringExtra("NAME")
        val imc = intent.getDoubleExtra("IMC_RESULT", 0.0)
        val imcFormatted = String.format("%.2f", imc)

        val category = when {
            imc < 18.5 -> "Abaixo do peso"
            imc < 25 -> "Normal"
            imc < 30 -> "Sobrepeso"
            else -> "Obesidade"
        }

        imcResultTv.text = "Olá, $name\nSeu IMC é $imcFormatted"
        imcCategoryTv.text = "Categoria: $category"

        backBt.setOnClickListener {
            finish()
        }
    }
}