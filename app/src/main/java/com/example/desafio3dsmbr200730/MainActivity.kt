package com.example.desafio3dsmbr200730

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var baseDatosHelper: BaseDatosHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar base de datos
        baseDatosHelper = BaseDatosHelper(this)

        // Referencias a los elementos del layout
        val inputGrados = findViewById<EditText>(R.id.inputGrados)
        val inputAlfa = findViewById<EditText>(R.id.inputAlfa)
        val inputValorT = findViewById<EditText>(R.id.inputValorT)
        val btnBuscarT = findViewById<Button>(R.id.btnBuscarT)
        val btnBuscarAlfa = findViewById<Button>(R.id.btnBuscarAlfa)
        val txtResultado = findViewById<TextView>(R.id.txtResultado)

        // Botón para buscar valor T
        btnBuscarT.setOnClickListener {
            val grados = inputGrados.text.toString().toIntOrNull()
            val alfa = inputAlfa.text.toString().toDoubleOrNull()

            if (grados != null && alfa != null) {
                val resultadoT = baseDatosHelper.obtenerValorT(grados, alfa)
                txtResultado.text = if (resultadoT != null) {
                    "Valor T correspondiente: $resultadoT"
                } else {
                    "No se encontró el valor T para esos datos."
                }
            } else {
                Toast.makeText(this, "Ingrese grados de libertad y alfa válidos", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón para buscar valor alfa
        btnBuscarAlfa.setOnClickListener {
            val grados = inputGrados.text.toString().toIntOrNull()
            val valorT = inputValorT.text.toString().toDoubleOrNull()

            if (grados != null && valorT != null) {
                val resultadoAlfa = baseDatosHelper.obtenerAlfaDesdeT(grados, valorT)
                txtResultado.text = if (resultadoAlfa != null) {
                    "Valor alfa correspondiente: $resultadoAlfa"
                } else {
                    "No se encontró el valor alfa para esos datos."
                }
            } else {
                Toast.makeText(this, "Ingrese grados de libertad y valor T válidos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}