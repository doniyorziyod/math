package com.example.math.ui.theme.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.math.R

@Composable
fun LevelView(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                LevelButton(text = "Easy",1, color = "#4CAF50", navController = navController)
                LevelButton(text = "Medium", 2, color = "#DFB22B", navController = navController)
                LevelButton(text = "Hard", 3,color = "#EC5448", navController = navController)
            }
        }
    }
}

@Composable
fun LevelButton(text: String, levelId: Int, color: String, navController: NavController){
    Button(
        onClick = { navController.navigate(route = "home_view/$levelId") },
        modifier = Modifier
            .width(170.dp)
            .height(70.dp)
            .padding(10.dp),
        border = BorderStroke(width = 2.dp, color = Color(android.graphics.Color.parseColor(color))),
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Text(text = text, color = Color(android.graphics.Color.parseColor(color)), fontSize = 18.sp)
    }
}

@Preview
@Composable
fun LevelScreen() {
    LevelView(navController = rememberNavController())
}