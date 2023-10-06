package com.kardelio.compose_spin_wheel

import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun SpinWheel(
    modifier: Modifier = Modifier,
    items: List<WheelSegment>,
    outlineSizeDp: Dp = 4.dp,
    outlineColor: Color = Color.Black,
    backgroundColor: Color = Color.Transparent,
    minimumFullRotations: Int = 10,
    winnerIndex: Int? = null,
    spinDuration: Int = 5000,
    spinAction: (() -> Unit)? = null
) {
    require(items.isNotEmpty()) { "Require at least one item!" }

    val numberOfItems by remember { mutableIntStateOf(items.size) }
    val segmentAngleSize by remember { mutableFloatStateOf(360f / numberOfItems) }
    val middleOfSegmentAngleSize by remember { mutableFloatStateOf(segmentAngleSize / 2) }
    val borderSize = with(LocalDensity.current) { outlineSizeDp.toPx() }
    val numberOfPreSpinsAngle by remember { mutableIntStateOf(minimumFullRotations * 360) }
    val textMeasurer = rememberTextMeasurer()

    val winnerFullRotateAngle by remember(winnerIndex) { mutableFloatStateOf(if (winnerIndex != null) (numberOfPreSpinsAngle + (segmentAngleSize * (items.size - (winnerIndex + 1))) + middleOfSegmentAngleSize) else 0f) }

    val rotationAngle by animateFloatAsState(
        if (winnerIndex != null) winnerFullRotateAngle else 0f,
        animationSpec = tween(spinDuration, easing = EaseOutCirc),
        label = "Spin Wheel rotation value"
    )

    Canvas(modifier = modifier
        .clickable {
            if (spinAction != null) {
                spinAction()
            }
        }) {
        rotate(rotationAngle) {
            drawCircle(color = backgroundColor, radius = size.width / 2 - borderSize)
            items.forEachIndexed { index, s ->
                rotate(segmentAngleSize * index) {
                    drawArc(
                        brush = s.backgroundBrush,
                        -90f,
                        segmentAngleSize,
                        true,
                        topLeft = Offset(-borderSize, borderSize)
                    )
                }
            }
            items.forEachIndexed { index, s ->
                rotate(segmentAngleSize * index) {
                    drawLine(
                        color = outlineColor,
                        start = Offset(size.width / 2, size.height / 2),
                        end = Offset(size.width / 2, 0f + borderSize),
                        strokeWidth = borderSize
                    )
                }
                rotate(segmentAngleSize * index + middleOfSegmentAngleSize) {
                    drawText(
                        textMeasurer,
                        s.text,
                        topLeft = Offset(
                            (size.width / 2) - ((size.width / 4) / items.size),
                            (size.height / 2) / 4
                        ),
                        style = TextStyle(
                            color = s.textColor,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(0f, 0f),
                                blurRadius = 8f,
                            ),
                            fontSize = ((size.width / 4) / items.size).sp,
                        )
                    )
                }
            }
            drawCircle(
                color = outlineColor,
                radius = size.width / 2 - borderSize / 2,
                style = Stroke(borderSize)
            )
        }
        val path = Path().apply {
            moveTo(size.width / 2 - 20f, -10f)
            lineTo(size.width / 2, 80f)
            lineTo(size.width / 2 + 20f, -10f)
            close()
        }
        drawPath(path, color = Color.Red)//TODO
    }
}

@Preview(showBackground = true)
@Composable
fun SpinWheelPreview(

) {
    Column {
        var winnerIndex by remember { mutableStateOf<Int?>(null) }
        SpinWheel(
            modifier = Modifier.size(400.dp),
            outlineSizeDp = 12.dp,
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
        SpinWheel(
            modifier = Modifier.size(200.dp),
            spinDuration = 10000,
            items = (1..12).map {
                WheelSegment(
                    it.toString(),
                    SolidColor(Color.Red),
                    textColor = Color.White
                )
            },
            winnerIndex = winnerIndex
        ) {}
        SpinWheel(
            modifier = Modifier.size(100.dp),
            minimumFullRotations = 2,
            outlineSizeDp = 2.dp,
            items = (1..6).map {
                WheelSegment(
                    it.toString(),
                    SolidColor(
                        Color(
                            Random.nextInt(256),
                            Random.nextInt(256),
                            Random.nextInt(256),
                            255
                        )
                    ),
                    textColor = Color.White
                )
            },
            winnerIndex = winnerIndex
        ) {}


    }
}

@Preview(showBackground = true)
@Composable
fun SpinWheelOtherPreview(

) {
    Column {
        var winnerIndex by remember { mutableStateOf<Int?>(null) }
        SpinWheel(
            modifier = Modifier.size(200.dp),
            backgroundColor = Color.Green,
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
        ) {}


    }
}