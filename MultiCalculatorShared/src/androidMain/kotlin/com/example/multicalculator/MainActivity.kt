package com.example.multicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalcView()
                }
            }
        }
    }

    @Composable
    fun calcView() {
        val display = remember { mutableStateOf("0") }

        Column(modifier = Modifier.background(Color.LightGray)) {
            CalcDisplay(display)

            Column {
                CalcOperationButton("+", display)
                CalcOperationButton("-", display)
                CalcOperationButton("*", display)
                CalcOperationButton("/", display)
            }

            for (i in 7 downTo 1 step 3) {
                CalcRow(display, i, 3)
            }

            Row {
                CalcNumericButton(0, display)
                CalcEqualsButton(display)
            }
        }
    }

    @Composable
    fun calcRow(display: MutableState<String>, startNum: Int, numButtons: Int) {
        val endNum = startNum + numButtons

        Row(modifier = Modifier.padding(0.dp)) {
            for (i in startNum until endNum) {
                CalcNumericButton(i, display)
            }
        }
    }

    @Composable
    fun calcDisplay(display: MutableState<String>) {
        androidx.compose.material.Text(
            text = display.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(5.dp)
        )
    }

    @Composable
    fun calcNumericButton(number: Int, display: MutableState<String>) {
        Button(
            onClick = { display.value += number.toString() },
            modifier = Modifier.padding(4.dp)
        ) {
            androidx.compose.material.Text(number.toString())
        }
    }

    @Composable
    fun calcOperationButton(operation: String, display: MutableState<String>) {
        Button(
            onClick = { /* Implement operation logic here */ },
            modifier = Modifier.padding(4.dp)
        ) {
            androidx.compose.material.Text(operation)
        }
    }

    @Composable
    fun calcEqualsButton(display: MutableState<String>) {
        Button(
            onClick = { display.value = "0" },
            modifier = Modifier.padding(4.dp)
        ) {
            androidx.compose.material.Text("=")
        }
    }
}

@Preview
@Composable
fun defaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}