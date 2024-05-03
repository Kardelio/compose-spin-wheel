package com.kardelio.compose_spin_wheel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSpinWheelExampleScreen()
        }
    }
}


@Composable
fun ComposeSpinWheelExampleScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFaFaFa))
    ) {

        item {
            Example1()
        }

        item {
            Example2()
        }

        item {
            Example3()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Example1() {
    var winnerIndex by remember { mutableStateOf<Int?>(null) }
    SpinWheel(
        sizeOfWheel = 200.dp,
        backgroundColor = Color.Green,
        outlineSizeDp = 0.dp,
        items = (1..6).map {
            WheelSegment(
                it.toString(),
                SolidColor(
                    Color.Transparent
                ),
                textColor = Color.White
            )
        },
        winnerIndex = winnerIndex
    ) {
    }
}


@Preview(showBackground = true)
@Composable
fun Example2() {
    var winnerIndex by remember { mutableStateOf<Int?>(null) }
    SpinWheel(
        sizeOfWheel = 400.dp,
        backgroundColor = Color.Green,
        outlineSizeDp = 0.dp,
        innerBorderLinesDp = 1.dp,
        outlineColor = Color.Transparent,
        innerBorderLinesColor = Color.Blue,
        items = (1..20).map {
            WheelSegment(
                it.toString(),
                SolidColor(
                    Color.Transparent
                ),
                textColor = Color.White
            )
        },
        winnerIndex = winnerIndex
    ) {}
}

@Preview(showBackground = true)
@Composable
fun Example3() {
    var winnerIndex by remember { mutableStateOf<Int?>(null) }
    SpinWheel(
        sizeOfWheel = 400.dp,
        outlineSizeDp = 4.dp,
        innerBorderLinesDp = 4.dp,
        items = (1..6).map {
            WheelSegment(
                it.toString(),
                Brush.linearGradient(
                    listOf(
                        Color(
                            Random.nextInt(256),
                            Random.nextInt(256),
                            Random.nextInt(256),
                            255
                        ),
                        Color(
                            Random.nextInt(256),
                            Random.nextInt(256),
                            Random.nextInt(256),
                            255
                        )
                    )
                ),
                textColor = Color.White
            )
        },
        winnerIndex = winnerIndex
    ) {}
}

@Preview(showBackground = true)
@Composable
fun ComposeSpinWheelExampleScreenPreview() {
    ComposeSpinWheelExampleScreen()
}