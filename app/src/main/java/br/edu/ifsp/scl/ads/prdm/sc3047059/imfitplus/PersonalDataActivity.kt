package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PersonalDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_personal_data)

        val heigth_et = findViewById<EditText>(R.id.height_et)
        val weight_et = findViewById<EditText>(R.id.weight_et)
        val calculate_bt = findViewById<Button>(R.id.calculate_bt)


        calculate_bt.setOnClickListener {
            val final_height = heigth_et.text.toString().toDoubleOrNull()
            val final_weight = weight_et.text.toString().toDoubleOrNull()
            if (final_weight != null && final_height != null && final_weight > 0){
                val imc = final_weight / (final_height * final_height)
                val intent = Intent(this, ImcResult::class.java)
                intent.putExtra("IMC_RESULT", imc)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Preencha os campos corretamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}