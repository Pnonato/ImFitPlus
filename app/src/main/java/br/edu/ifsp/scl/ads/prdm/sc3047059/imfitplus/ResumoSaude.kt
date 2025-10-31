package br.edu.ifsp.scl.ads.prdm.sc3047059.imfitplus

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

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
        val calculaAguaBt = findViewById<Button>(R.id.calcula_ingestao_bt)


        val weight = intent.getDoubleExtra("WEIGHT", 0.0)
        val height = intent.getDoubleExtra("HEIGHT", 0.0)
        val nome = intent.getStringExtra("NAME")
        val categoria = intent.getStringExtra("CATEGORIA")
        val imc = intent.getDoubleExtra("IMC_RESULT", 0.0)
        val gastoC = intent.getDoubleExtra("GASTOC", 0.0)
        val pesoIdeal = intent.getDoubleExtra("PESOIDEAL", 0.0)
        val recomendacaoAgua = weight * 0.35

        categoriaTv.text = categoria
        nomeTv.text = nome
        imcTv.text = String.format("IMC: %,.2f", imc)
        gastoCaloricoTv.text =  String.format("GASTO CALÓRICO: %,.2f", gastoC)
        pesoIdealTv.text =  String.format("PESO IDEAK:%,.1f", pesoIdeal)


        calculaAguaBt.setOnClickListener {
            ingestaoAguaTv.text = String.format("INGESTÃO L/DIA: %,.2f", recomendacaoAgua)
        }




    }
}