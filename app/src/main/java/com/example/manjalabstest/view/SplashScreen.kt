package com.example.manjalabstest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.manjalabstest.R

@Composable
fun SplashScreen(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
    val progress by animateLottieCompositionAsState(composition)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3A539B))
    ) {
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.size(200.dp)
        )
    }

    LaunchedEffect(progress) {
        if (progress == 1f) {
            navController.navigate("main") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}