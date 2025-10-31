package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CaloriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calories)

        val weight = intent.getDoubleExtra("WEIGHT", 0.0)
        val height = intent.getDoubleExtra("HEIGHT", 0.0)
        val age = intent.getIntExtra("AGE", 0)
        val gender = intent.getStringExtra("GENDER")

        val calculateTmbBt = findViewById<Button>(R.id.calculate_tmb_bt)
        val idealWeightBt = findViewById<Button>(R.id.ideal_weight_bt)
        val tmbResultTv = findViewById<TextView>(R.id.tmb_result_tv)
        val backBt = findViewById<Button>(R.id.back_bt)

        calculateTmbBt.setOnClickListener {
            if (weight > 0 && height > 0 && age > 0 && gender != null){
                val tmb = if (gender == "M") {
                    66 + (13.7 * weight) + (5 * height * 100) - (6.8 * age)
                } else {
                    655 + (9.6 * weight) + (1.8 * height * 100) - (4.7 * age)
                }
                tmbResultTv.text = String.format("%,.0f kcal/dia", tmb)
                tmbResultTv.textSize = 36f
            } else {
                Toast.makeText(this, "Dados incompletos para calcular TMB", Toast.LENGTH_LONG).show()
            }
        }

        backBt.setOnClickListener {
            finish()
        }

        idealWeightBt.setOnClickListener {
            val intentIdealWeight = Intent(this, IdealWeight::class.java)
            intentIdealWeight.putExtra("WEIGHT", weight)
            intentIdealWeight.putExtra("HEIGHT", height)
            startActivity(intentIdealWeight)
        }

    }
}
