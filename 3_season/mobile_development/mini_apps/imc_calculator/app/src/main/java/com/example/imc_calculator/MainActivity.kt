package com.example.imc_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imc_calculator.ui.theme.Imc_calculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Imc_calculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ImcCalculator(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ImcCalculator(name: String, modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    fun calcIMC() {
        val weightVal = weight.toFloatOrNull()
        val heightVal = height.toFloatOrNull()

        if (weightVal != null && heightVal != null && weightVal > 0 && heightVal > 0) {
            val imc = weightVal / (heightVal*heightVal)
            val category = when {
                imc < 18.5 -> "Magro"
                imc < 24.9 -> "Normal"
                imc < 29.9 -> "Gordo"
                else -> "Obeso"
            }

            message = String.format("Seu imc é %.2f\nVocê está %s", imc, category)
        } else {
            message = "Dados invalidos"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "IMC Calculator",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weigth (kg)") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height (m)") },
                modifier = Modifier.weight(1f)
            )
        }
        Button(onClick = { calcIMC() }) {Text("Calcular")}

        if (message.isNotEmpty()) {
            Text(
                text = message,
                textAlign = TextAlign.Center
            )
        }
    }
}