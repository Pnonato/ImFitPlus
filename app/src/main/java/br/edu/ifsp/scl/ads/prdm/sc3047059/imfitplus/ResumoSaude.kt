package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model.AppDatabase
import br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model.History
import br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus.model.HistoryDao
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class ResumoSaude : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resumo_saude)

        val categoriaTv = findViewById<TextView>(R.id.imc_category_tv)
        val nomeTv = findViewById<TextView>(R.id.nome_tv)
        val imcTv = findViewById<TextView>(R.id.imc_tv)
        val pesoIdealTv = findViewById<TextView>(R.id.peso_ideal_tv)
        val gastoCaloricoTv = findViewById<TextView>(R.id.gasto_calorico_tv)
        val ingestaoAguaTv = findViewById<TextView>(R.id.recomendacao_agua_tv)
        val freqMaxima = findViewById<TextView>(R.id.freq_maxima)
        val calculaAguaBt = findViewById<Button>(R.id.calcula_ingestao_bt)
        val dadosCadastradosBt = findViewById<Button>(R.id.dados_cadastrados_bt)
        val catTreino = findViewById<TextView>(R.id.cat_frequencia)
        val backBt = findViewById<Button>(R.id.back_bt)
        val userId = intent.getIntExtra("USER_ID", -1)

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
        val idadeAnos = intent.getIntExtra("IDADE_ANOS", -1)




        val frequenciaMaxima = 220 - idadeAnos
        val freq50 = frequenciaMaxima / 2
        val freq60 = frequenciaMaxima * 0.6
        val freq70 = frequenciaMaxima * 0.7
        val freq80 = frequenciaMaxima * 0.8
        val recomendacaoAgua = weight * 0.035
        val imcFinal = String.format("%.2f", imc).toDouble()
        val pesoIdealFinal = String.format("%.1f", pesoIdeal).toDouble()
        val gastoCFinal = String.format("%.2f", gastoC).toDouble()

       if(frequenciaMaxima > freq50 && frequenciaMaxima < freq60){
           catTreino.text = "Sua Categoria: LEVE"
       } else{
           if(frequenciaMaxima > freq60 && frequenciaMaxima < freq70){
               catTreino.text = "Sua Categoria: ZONA QUEIMA DE GORDURA"
           } else{
               if(frequenciaMaxima > freq70 && frequenciaMaxima < freq80){
                   catTreino.text = "Sua Categoria: ZONA AEROBICA"

               } else {
                   catTreino.text = "Sua Categoria: ZONA ANAEROBICA"

               }
           }
       }


        val db = AppDatabase.getDatabase(this)
        val historyDao = db.historyDao()

        lifecycleScope.launch {

            val historyList = historyDao.getHistoryByUser(userId)

            val history = History(
                userId = userId,
                userName = nome ?: "",
                imc = imcFinal,
                imcCategory = categoria ?: "",
                tmb = gastoCFinal,
                idealWeight = pesoIdealFinal,
                freqCardiaca = frequenciaMaxima,
                ingestaoAgua = recomendacaoAgua
            )

            historyDao.insertHistory(history)
        }

        categoriaTv.text = "Categoria: $categoria"
        nomeTv.text = "Nome: $nome"
        imcTv.text = String.format("IMC: %,.2f", imc)
        gastoCaloricoTv.text = String.format("Gasto Calórico: %,.0f kcal/dia", gastoC)
        pesoIdealTv.text = String.format("Peso Ideal: %,.1f kg", pesoIdeal)
        freqMaxima.text = "Frequencia Maxima: $frequenciaMaxima"

        calculaAguaBt.setOnClickListener {
            ingestaoAguaTv.text = String.format("Ingestão recomendada: %,.2f L/dia", recomendacaoAgua)
        }

        dadosCadastradosBt.setOnClickListener {
            val dadosCadastrados = Intent(this, DataBaseView::class.java)
            dadosCadastrados.putExtra("WEIGHT", weight)
            dadosCadastrados.putExtra("HEIGHT", height)
            dadosCadastrados.putExtra("NAME", nome)
            dadosCadastrados.putExtra("CATEGORIA", categoria)
            dadosCadastrados.putExtra("IMC_RESULT", imcFinal)
            dadosCadastrados.putExtra("GASTOC", gastoCFinal)
            dadosCadastrados.putExtra("PESOIDEAL", pesoIdealFinal)
            dadosCadastrados.putExtra("AGE", age)
            dadosCadastrados.putExtra("GENDER", gender)
            dadosCadastrados.putExtra("ACTIVITY_LVL", activityLvl)
            dadosCadastrados.putExtra("RECOMENDACAO_AGUA", recomendacaoAgua)
            dadosCadastrados.putExtra("USER_ID", userId)
            dadosCadastrados.putExtra("IDADE_ANOS", idadeAnos)


            startActivity(dadosCadastrados)
        }

        backBt.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}
