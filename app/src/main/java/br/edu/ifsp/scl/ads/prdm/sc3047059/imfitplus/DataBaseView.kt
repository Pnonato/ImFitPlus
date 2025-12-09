package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model.AppDatabase
import kotlinx.coroutines.launch

class DataBaseView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_base_view)


        val weight = intent.getDoubleExtra("WEIGHT", 0.0)
        val height = intent.getDoubleExtra("HEIGHT", 0.0)
        val nome = intent.getStringExtra("NAME")
        val categoria = intent.getStringExtra("CATEGORIA")
        val imc = intent.getDoubleExtra("IMC_RESULT", 0.0)
        val gastoC = intent.getDoubleExtra("GASTOC", 0.0)
        val pesoIdeal = intent.getDoubleExtra("PESOIDEAL", 0.0)
        val age = intent.getIntExtra("AGE", 0)
        val gender = intent.getStringExtra("GENDER")
        val activityLvl = intent.getStringExtra("ACTIVITY_LVL")
        val recomendacaoAgua = intent.getDoubleExtra("RECOMENDACAO_AGUA", 0.0)

        val backButton = findViewById<Button>(R.id.back_bt)
        val usersTv = findViewById<TextView>(R.id.users_tv)
        val historyTv = findViewById<TextView>(R.id.history_tv)



        val db = AppDatabase.getDatabase(this)
        val userDao = db.usuarioDao()
        val historyDao = db.historyDao()

        lifecycleScope.launch {
            val users = userDao.getUsers()
            val usersText = buildString {
                append("Usuários cadastrados:\n\n")
                for (u in users) {

                    val alturaFormatada = String.format("%.2f m", u.altura)
                    val pesoFormatado = String.format("%.1f kg", u.peso)

                    append("ID: ${u.id}\n")
                    append("Nome: ${u.nome}\n")
                    append("Idade: ${u.idade} anos\n")
                    append("Sexo: ${u.sexo}\n")
                    append("Altura: $alturaFormatada\n")
                    append("Peso: $pesoFormatado\n")
                    append("Nível atividade: ${u.nivelAtividade}\n")
                    append("\n----------------------\n\n")
                }
            }
            usersTv.text = usersText

            val historyList = historyDao.getAllHistory()
            val historyText = buildString {
                append("Histórico:\n\n")
                for (h in historyList) {

                    val imcFormatado = String.format("%.2f", h.imc)
                    val tmbFormatado = String.format("%.0f kcal/dia", h.tmb)
                    val pesoIdealFormatado = String.format("%.1f kg", h.idealWeight)

                    append("ID: ${h.id}\n")
                    append("Nome: ${h.userName}\n")
                    append("IMC: $imcFormatado\n")
                    append("Categoria IMC: ${h.imcCategory}\n")
                    append("TMB (gasto): $tmbFormatado\n")
                    append("Peso ideal: $pesoIdealFormatado\n")
                    append("\n----------------------\n\n")
                }
            }
            historyTv.text = historyText
        }


        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}
