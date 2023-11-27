package com.example.math.ui.theme.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.math.R
import com.example.math.SharedPreference
import com.example.math.models.Record
import com.example.math.navigation.Screens

@Preview
@Composable
fun RecordScreen() {
    RecordView(rememberNavController(), Record(14, 2, 2, true))
}

@Composable
fun RecordView(navController: NavController, record: Record) {
    val appBarColor = colorResource(id = R.color.blue50)
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    BackHandler(enabled = true, onBack = {
        navController.navigate(Screens.Level.route)
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = appBarColor)
                .padding(0.dp, 8.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val shared = SharedPreference.getInstance(LocalContext.current)
            if (record.newRecord) {
                Log.d("TAG", "shared: ${shared.getRecord(record.level)}")
                    Text(
                        text = "New Record",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.black)
                    )
            }
            Text(
                text = "Score: ${record.correct}",
                fontSize = 48.sp,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(0.dp, 16.dp)
            )
            Box(
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.white),
                    )
                    .border(
                        border = BorderStroke(
                            2.dp, colorResource(
                                id = R.color.gray10
                            )
                        ), shape = RoundedCornerShape(20.dp)
                    )
                    .padding(24.dp, 8.dp)
            ) {
                Text(
                    text = "Level: ${getLevelName(record.level)}",
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.black80),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Total solved: ${record.correct + record.incorrect}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(
                    id = R.color.blue2
                )
            )
            Text(
                text = "Incorrect: ${record.incorrect}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(
                    id = R.color.blue2
                )
            )
            if (record.correct != 0 && record.incorrect != 0) Text(
                text = "Accuracy: ${record.correct * 100 / (record.correct + record.incorrect)}%",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(
                    id = R.color.blue2
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Button(
                    onClick = { navController.navigate(Screens.Level.route) },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green50)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Rounded.Home, contentDescription = "Go home")
                    Text(text = "Home")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_200)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Rounded.Refresh, contentDescription = "Go home")
                    Text(text = "Restart ")
                }

            }
        }
    }
}

fun getLevelName(id: Int): String {
    return when (id) {
        0 -> "Easy"
        1 -> "Medium"
        else -> "hard"
    }
}