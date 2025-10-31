package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class IdealWeight : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ideal_weight)

        val idealWeightResultTv = findViewById<TextView>(R.id.idealWeight_result_tv)
        val calculateIdealWeightBt = findViewById<Button>(R.id.calculate_idealWeight_bt)
        val backBt = findViewById<Button>(R.id.back_bt)

        val weight = intent.getDoubleExtra("WEIGHT", 0.0)
        val height = intent.getDoubleExtra("HEIGHT", 0.0)

        calculateIdealWeightBt.setOnClickListener {
            if (height > 0 && weight > 0){
               val idealWeight = 22 * (height * height)
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
            finish()
        }
    }
}