package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       enableEdgeToEdge()
       setContent {
           MyApplicationTheme {
               Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   EmiCalculator(modifier = Modifier.padding(innerPadding))
               }
           }
       }
   }
}


@Composable
fun EmiCalculator(modifier: Modifier = Modifier) {
   var loanAmount by remember { mutableStateOf(TextFieldValue("")) }
   var interestRate by remember { mutableStateOf(TextFieldValue("")) }
   var tenure by remember { mutableStateOf(TextFieldValue("")) }
   var income by remember { mutableStateOf(TextFieldValue("")) }
   var expenses by remember { mutableStateOf(TextFieldValue("")) }


   var emiResult by remember { mutableStateOf("") }
   var budgetResult by remember { mutableStateOf("") }


   Column(modifier = modifier.padding(12.dp)) {
       Text(
           text = "EMI & Budget Calculator",
           style = MaterialTheme.typography.titleMedium
       )


       Spacer(modifier = Modifier.height(8.dp))
       OutlinedTextField(
           value = loanAmount,
           onValueChange = { loanAmount = it },
           label = { Text("Loan Amount (CAD)") },
           modifier = Modifier.fillMaxWidth(),
           singleLine = true
       )


       Spacer(modifier = Modifier.height(6.dp))
       OutlinedTextField(
           value = interestRate,
           onValueChange = { interestRate = it },
           label = { Text("Interest Rate (%)") },
           modifier = Modifier.fillMaxWidth(),
           singleLine = true
       )


       Spacer(modifier = Modifier.height(6.dp))
       OutlinedTextField(
           value = tenure,
           onValueChange = { tenure = it },
           label = { Text("Tenure (months)") },
           modifier = Modifier.fillMaxWidth(),
           singleLine = true
       )


       Spacer(modifier = Modifier.height(8.dp))
       OutlinedTextField(
           value = income,
           onValueChange = { income = it },
           label = { Text("Monthly Income (CAD)") },
           modifier = Modifier.fillMaxWidth(),
           singleLine = true
       )


       Spacer(modifier = Modifier.height(6.dp))
       OutlinedTextField(
           value = expenses,
           onValueChange = { expenses = it },
           label = { Text("Monthly Expenses (CAD)") },
           modifier = Modifier.fillMaxWidth(),
           singleLine = true
       )


       Spacer(modifier = Modifier.height(10.dp))
       Button(
           onClick = {
               val P = loanAmount.text.toDoubleOrNull() ?: 0.0
               val r = (interestRate.text.toDoubleOrNull() ?: 0.0) / 12 / 100
               val n = tenure.text.toIntOrNull() ?: 0
               val emi = if (r > 0 && n > 0) {
                   val numerator = P * r * Math.pow(1 + r, n.toDouble())
                   val denominator = Math.pow(1 + r, n.toDouble()) - 1
                   numerator / denominator
               } else 0.0


               emiResult = "Monthly EMI: CAD $%.2f".format(emi)


               val monthlyIncome = income.text.toDoubleOrNull() ?: 0.0
               val monthlyExpenses = expenses.text.toDoubleOrNull() ?: 0.0
               val balance = monthlyIncome - (emi + monthlyExpenses)


               budgetResult = if (balance >= 0) {
                   "Savings after EMI & Expenses: CAD $%.2f".format(balance)
               } else {
                   "Deficit after EMI & Expenses: CAD $%.2f".format(-balance)
               }
           },
           modifier = Modifier.fillMaxWidth()
       ) {
           Text("Calculate")
       }


       Spacer(modifier = Modifier.height(12.dp))
       if (emiResult.isNotEmpty()) {
           Text(
               text = emiResult,
               style = MaterialTheme.typography.bodyMedium,
               color = MaterialTheme.colorScheme.primary
           )
       }


       Spacer(modifier = Modifier.height(6.dp))
       if (budgetResult.isNotEmpty()) {
           Text(
               text = budgetResult,
               style = MaterialTheme.typography.bodyMedium,
               color = MaterialTheme.colorScheme.secondary
           )
       }
   }
}
