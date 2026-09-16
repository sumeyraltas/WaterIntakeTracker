package com.example.waterintake.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.waterintake.presentation.theme.BlueWaveDeep
import com.example.waterintake.presentation.theme.CyanAccent
import com.example.waterintake.presentation.theme.CyanPrimary
import kotlin.math.PI
import kotlin.math.sin

/**
 * Endüstriyel dairesel sıvı göstergesi:
 * İki katmanlı sinüzoidal dalga, yumuşak yükseklik interpolasyonu,
 * derinlik gradyanı ve çevreleyici hassas gösterge halkası.
 */
@Composable
fun LiquidWaveProgress(
    progress: Float, // 0.0f - 1.0f
    modifier: Modifier = Modifier,
    size: Dp = 260.dp,
    content: @Composable () -> Unit = {}
) {
    // Sıvı yüksekliği animasyonu
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "water_level_anim"
    )

    // Sürekli dalga hareketi
    val infiniteTransition = rememberInfiniteTransition(label = "wave_motion")
    val wavePhase1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase_1"
    )

    val wavePhase2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase_2"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = this.size.width
            val height = this.size.height
            val radius = width / 2f
            val center = Offset(width / 2f, height / 2f)

            // Dış sınır çemberi path'i (Sıvı taşmasın diye kırpma)
            val circleClip = Path().apply {
                addOval(androidx.compose.ui.geometry.Rect(0f, 0f, width, height))
            }

            clipPath(circleClip) {
                // Arka plan boş sıvı haznesi
                drawRect(
                    color = Color(0x1A22D3EE),
                    topLeft = Offset.Zero,
                    size = this.size
                )

                // Sıvı taban seviyesi (Y ekseni ters çalışır)
                val baseWaterY = height * (1f - animatedProgress)
                val waveAmplitude = 18f * (1f - (animatedProgress - 0.5f) * (animatedProgress - 0.5f) * 4f).coerceAtLeast(0.2f)

                // Arka dalga katmanı (Deep Blue Wave)
                val backWavePath = Path().apply {
                    moveTo(0f, height)
                    lineTo(0f, baseWaterY)
                    for (x in 0..width.toInt() step 6) {
                        val y = baseWaterY + sin((x / width * 2f * PI.toFloat()) + wavePhase2) * (waveAmplitude * 0.8f)
                        lineTo(x.toFloat(), y)
                    }
                    lineTo(width, height)
                    close()
                }
                drawPath(
                    path = backWavePath,
                    brush = Brush.verticalGradient(
                        colors = listOf(BlueWaveDeep.copy(alpha = 0.55f), BlueWaveDeep.copy(alpha = 0.85f)),
                        startY = baseWaterY,
                        endY = height
                    )
                )

                // Ön dalga katmanı (Parlak Cyan / Su Gradyanı)
                val frontWavePath = Path().apply {
                    moveTo(0f, height)
                    lineTo(0f, baseWaterY)
                    for (x in 0..width.toInt() step 6) {
                        val y = baseWaterY + sin((x / width * 2f * PI.toFloat()) + wavePhase1) * waveAmplitude
                        lineTo(x.toFloat(), y)
                    }
                    lineTo(width, height)
                    close()
                }
                drawPath(
                    path = frontWavePath,
                    brush = Brush.verticalGradient(
                        colors = listOf(CyanAccent.copy(alpha = 0.85f), CyanPrimary.copy(alpha = 0.95f), BlueWaveDeep),
                        startY = baseWaterY,
                        endY = height
                    )
                )
            }

            // Hassas endüstriyel dış bezel ve kadran çizgisi
            drawCircle(
                color = CyanAccent.copy(alpha = 0.35f),
                radius = radius - 2f,
                style = Stroke(width = 3.dp.toPx())
            )
        }

        // Merkezdeki gösterge metinleri
        content()
    }
}
