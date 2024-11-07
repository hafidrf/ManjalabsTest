package com.example.manjalabstest.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manjalabstest.viewmodel.StringValidatorViewModel

@Composable
fun MainScreen(viewModel: StringValidatorViewModel) {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    val validationResult by viewModel.result.collectAsState()

    val validationColor = if (validationResult.isValid) Color(0xFF4CAF50) else Color(0xFFF44336) // Hijau  alid, merah invalid

    val scale by animateFloatAsState(
        targetValue = if (validationResult.isValid || validationResult.modifiedString != null) 1.2f else 1f,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE0F7FA)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "String Validator",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFF3A539B)
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Masukkan string", color = Color(0xFF3A539B)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color(0xFF3A539B),
                unfocusedIndicatorColor = Color(0xFFB0BEC5)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { viewModel.validstring(input.text) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3A539B)),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(vertical = 8.dp)
        ) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Validasi", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Crossfade(targetState = validationResult.isValid) { isValid ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .scale(scale)
            ) {
                Icon(
                    imageVector = if (isValid) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = null,
                    tint = validationColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isValid) {
                        "VALID: ${validationResult.modifiedString}"
                    } else {
                        "INVALID"
                    },
                    color = validationColor,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}