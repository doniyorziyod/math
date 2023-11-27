package com.example.math.ui.theme.screens

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.math.R
import com.example.math.SharedPreference
import com.example.math.models.Question
import kotlinx.coroutines.delay

@Preview
@Composable
fun HomeScreen() {
    HomeView(navController = rememberNavController(), level = 1)
}

@Composable
fun HomeView(navController: NavController, level: Int) {
    val correct = remember { mutableIntStateOf(0) }
    val total = remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val time = remember { mutableIntStateOf(20) }
    val question = remember { mutableStateOf(Question.generate(level)) }
    val appBarColor = colorResource(id = R.color.blue50)
    val testTextColor = remember { mutableStateOf(Color.Black) }
    val backColor = colorResource(id = R.color.test_background)
    val testBackgroundColor = remember { Animatable(backColor) }
    val openDialog = remember { mutableStateOf(false) }
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher


    BackHandler(true, onBack = {
        openDialog.value = true
    })

    LaunchedEffect(Unit) {
        while (time.intValue > 0) {
            delay(1000)
            time.intValue--
        }
        val shared = SharedPreference.getInstance(context)
        val newRecord = shared.getRecord(level) < correct.intValue
        if (newRecord) shared.setRecord(level, correct.intValue)
        navController.navigate(route = "record_view/${correct.intValue}/${total.intValue - correct.intValue}/$level/$newRecord")
    }
    if (openDialog.value) ShowDialog(navController = navController, openDialog)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // App bar
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Time: ${time.intValue}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                )
                Text(
                    text = "Score: ${correct.intValue}",
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp
                )
            }
        }
        // Misol chiqadigan joy
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true),
            contentAlignment = Alignment.Center

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(20.dp, 8.dp)
                    .width(220.dp)
                    .height(40.dp)
            ) {
                ProblemText(question.value.var1.toString(), testTextColor)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.blank_icon),
                    contentDescription = "Symbol blank",
                    modifier = Modifier.size(24.dp),
                    tint = testTextColor.value
                )
                Spacer(modifier = Modifier.width(8.dp))
                ProblemText(text = question.value.var2.toString(), testTextColor)
                ProblemText(text = " = ", testTextColor)
                ProblemText(text = question.value.result.toString(), testTextColor)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(
                    color = colorResource(id = R.color.gray10),
                    shape = RoundedCornerShape(64.dp)
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Row(modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp)) {
                    SymbolButton(
                        0,
                        R.drawable.minus_icon,
                        correct = correct,
                        total = total,
                        question,
                        level
                    )
                    SymbolButton(
                        1,
                        R.drawable.plus_icon,
                        correct = correct,
                        total = total,
                        question,
                        level
                    )
                }
                Row {
                    SymbolButton(
                        2,
                        R.drawable.divide_icon,
                        correct = correct,
                        total = total,
                        question,
                        level
                    )
                    SymbolButton(
                        3,
                        R.drawable.multiply_icon,
                        correct = correct,
                        total = total,
                        question,
                        level
                    )
                }
            }
        }

    }
}

@Composable
fun ShowDialog(navController: NavController, isDialogOpen: MutableState<Boolean>) {
    if (isDialogOpen.value) {
        AlertDialog (
            onDismissRequest = { isDialogOpen.value = false },
            confirmButton = {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) { Text(text = "Yes") }
            },
            dismissButton = {
                Button(onClick = {
                    isDialogOpen.value = false
                }, colors = ButtonDefaults.buttonColors(Color.Gray)) { Text(text = "Cancel") }
            },
            title = { Text("Do you really want to exit the game?") })
    }
}

@Composable
fun ProblemText(text: String, color: MutableState<Color>) {
    Text(text = text, fontSize = 28.sp, fontWeight = FontWeight.Normal, color = color.value)
}

@Composable
fun RowScope.SymbolButton(
    id: Int,
    imageResource: Int,
    correct: MutableIntState,
    total: MutableIntState,
    question: MutableState<Question>,
    levelId: Int
) {
    Button(
        modifier = Modifier
            .weight(1f, true)
            .aspectRatio(1f, true)
            .padding(8.dp),
        onClick = {
            total.intValue++

                if (question.value.symbol == id) {
                    correct.intValue++
                }
                question.value = Question.generate(levelId)
        },
        shape = RoundedCornerShape(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Image(painter = painterResource(id = imageResource), contentDescription = "minus icon")
    }
}