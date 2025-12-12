package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.content.Intent
import android.icu.util.LocaleData
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model.AppDatabase
import br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model.Usuario
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

class PersonalDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_personal_data)

        val heightEt = findViewById<EditText>(R.id.height_et)
        val weightEt = findViewById<EditText>(R.id.weight_et)
        val calculateBt = findViewById<Button>(R.id.calculate_bt)
        val nameEt = findViewById<EditText>(R.id.name_et)
        val ageEt = findViewById<EditText>(R.id.idade_et)
        val genderRg = findViewById<RadioGroup>(R.id.gender_rg)
        val termsCb = findViewById<CheckBox>(R.id.terms_cb)
        val backBt = findViewById<Button>(R.id.back_bt)
        val activitySpinner = findViewById<Spinner>(R.id.activity_spinner)

        fun calculateAge(birthDate: LocalDate): Int {
            val currentDate = LocalDate.now()
            return Period.between(birthDate, currentDate).years
        }



        calculateBt.setOnClickListener {
            val selectedActivity = activitySpinner.selectedItem.toString()
            if (!termsCb.isChecked) {
                Toast.makeText(
                    this,
                    "É necessário concordar com os termos para continuar.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val name = nameEt.text.toString().trim()
                val heightStr = heightEt.text.toString().trim()
                val weightStr = weightEt.text.toString().trim()
                val ageStr = ageEt.text.toString().trim()
                val ageDate = LocalDate.parse(ageStr)
                val idadeAnos = calculateAge(ageDate)
                val selectedId = genderRg.checkedRadioButtonId

                if (name.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || selectedId == -1) {
                    Toast.makeText(
                        this,
                        "Preencha todos os campos antes de continuar",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                val height = heightStr.toDoubleOrNull()
                val weight = weightStr.toDoubleOrNull()
                val age = ageStr.toIntOrNull()
                val gender = when (selectedId) {
                    R.id.men_rb -> "M"
                    R.id.women_rb -> "F"
                    else -> ""
                }

                if (height == null || weight == null || height <= 0 || weight <= 0) {
                    Toast.makeText(this, "Digite valores numéricos válidos", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val imc = weight / (height * height)

                val db = AppDatabase.getDatabase(this)
                val userDao = db.usuarioDao()

                val user = Usuario(
                    nome = name,
                    idade = ageStr,
                    idadeAnos = idadeAnos,
                    sexo = gender,
                    altura = height,
                    peso = weight,
                    nivelAtividade = selectedActivity
                )


                lifecycleScope.launch {
                    val userId = userDao.insertUser(user).toInt()

                    val intent = Intent(this@PersonalDataActivity, ImcResult::class.java).apply {
                        putExtra("USER_ID", userId)
                        putExtra("IMC_RESULT", imc)
                        putExtra("NAME", name)
                        putExtra("WEIGHT", weight)
                        putExtra("HEIGHT", height)
                        putExtra("AGE", age)
                        putExtra("GENDER", gender)
                        putExtra("ACTIVITY_LVL", selectedActivity)
                        putExtra("IDADE_ANOS", idadeAnos)
                    }

                    startActivity(intent)
                }
            }

        }

        backBt.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()

        }
    }
}
