package com.example.multicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    CalcView()
                }
            }
        }
    }
}

@Composable
fun CalcView() {
    var leftNumber by rememberSaveable { mutableStateOf(0) }
    var rightNumber by rememberSaveable { mutableStateOf(0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }
    var displayText by rememberSaveable { mutableStateOf("") }

    if (complete && operation.isNotEmpty()) {
        var answer = 0
        when (operation) {
            "+" -> answer = leftNumber + rightNumber
            "-" -> answer = leftNumber - rightNumber
            "*" -> answer = leftNumber * rightNumber
            "/" -> answer = if (rightNumber != 0) leftNumber / rightNumber else 0
        }
        displayText = answer.toString()
    } else if (operation.isNotEmpty() && !complete) {
        displayText = rightNumber.toString()
    } else {
        displayText = leftNumber.toString()
    }

    fun numberPress(btnNum: Int) {
        if (complete) {
            leftNumber = 0
            rightNumber = 0
            operation = ""
            complete = false
        }
        if (operation.isNotEmpty() && !complete) {
            rightNumber = rightNumber * 10 + btnNum
        } else if (operation.isEmpty() && !complete) {
            leftNumber = leftNumber * 10 + btnNum
        }
    }

    fun operationPress(op: String) {
        if (!complete) {
            operation = op
        }
    }

    fun equalsPress() {
        complete = true
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = displayText, style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        CalcRow { number -> numberPress(number) }
        Spacer(modifier = Modifier.height(8.dp))
        CalcOperationRow { op -> operationPress(op) }
        Spacer(modifier = Modifier.height(8.dp))
        CalcEqualsButton(onPress = { equalsPress() })
    }
}

@Composable
fun CalcRow(onPress: (Int) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        (1..3).forEach { number ->
            CalcNumericButton(number = number, onPress = onPress)
        }
    }
}

@Composable
fun CalcNumericButton(number: Int, onPress: (Int) -> Unit) {
    Button(onClick = { onPress(number) }, modifier = Modifier.size(64.dp)) {
        Text(text = number.toString())
    }
}

@Composable
fun CalcOperationRow(onPress: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        listOf("+", "-", "*", "/").forEach { operation ->
            CalcOperationButton(operation = operation, onPress = onPress)
        }
    }
}

@Composable
fun CalcOperationButton(operation: String, onPress: (String) -> Unit) {
    Button(onClick = { onPress(operation) }, modifier = Modifier.size(64.dp)) {
        Text(text = operation)
    }
}

@Composable
fun CalcEqualsButton(onPress: () -> Unit) {
    Button(onClick = onPress, modifier = Modifier.fillMaxWidth()) {
        Text(text = "=")
    }
}
