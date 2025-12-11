package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class IdealWeight : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ideal_weight)

        val idealWeightResultTv = findViewById<TextView>(R.id.idealWeight_result_tv)
        val calculateIdealWeightBt = findViewById<Button>(R.id.calculate_idealWeight_bt)
        val nextBt = findViewById<Button>(R.id.next_bt)
        val backBt = findViewById<Button>(R.id.back_bt)

        val weight = intent.getDoubleExtra("WEIGHT", 0.0)
        val height = intent.getDoubleExtra("HEIGHT", 0.0)
        val nome = intent.getStringExtra("NAME")
        val categoria = intent.getStringExtra("CATEGORIA")
        val imc = intent.getDoubleExtra("IMC_RESULT", 0.0)
        val gastoC = intent.getDoubleExtra("GASTOC", 0.0)
        val age = intent.getIntExtra("AGE", 0)
        val gender = intent.getStringExtra("GENDER")
        val activityLvl = intent.getStringExtra("ACTIVITY_LVL")
        val userId = intent.getIntExtra("USER_ID", -1)


        val idealWeight = 22 * (height * height)

        calculateIdealWeightBt.setOnClickListener {
            if (height > 0 && weight > 0) {
                val diff = weight - idealWeight

                val status = when {
                    diff > 0 -> "Você está aproximadamente %.1f kg acima do peso ideal.".format(diff)
                    diff < 0 -> "Você está aproximadamente %.1f kg abaixo do peso ideal.".format(-diff)
                    else -> "Você está exatamente no peso ideal!"
                }

                idealWeightResultTv.text = "Peso Ideal: %.1f kg\n\n$status".format(idealWeight)
                idealWeightResultTv.textSize = 32f
            } else {
                Toast.makeText(this, "Altura e peso inválidos", Toast.LENGTH_LONG).show()
            }
        }

        backBt.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        nextBt.setOnClickListener {
            val intentResumoSaude = Intent(this, ResumoSaude::class.java)
            intentResumoSaude.putExtra("WEIGHT", weight)
            intentResumoSaude.putExtra("HEIGHT", height)
            intentResumoSaude.putExtra("NAME", nome)
            intentResumoSaude.putExtra("CATEGORIA", categoria)
            intentResumoSaude.putExtra("IMC_RESULT", imc)
            intentResumoSaude.putExtra("GASTOC", gastoC)
            intentResumoSaude.putExtra("PESOIDEAL", idealWeight)
            intentResumoSaude.putExtra("AGE", age)
            intentResumoSaude.putExtra("GENDER", gender)
            intentResumoSaude.putExtra("ACTIVITY_LVL", activityLvl)
            intentResumoSaude.putExtra("USER_ID", userId)


            startActivity(intentResumoSaude)
        }
    }
}
