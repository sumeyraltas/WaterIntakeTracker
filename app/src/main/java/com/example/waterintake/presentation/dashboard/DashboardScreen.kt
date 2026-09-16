package com.example.waterintake.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.LocalDrink
import androidx.compose.material.icons.automirrored.rounded.Undo
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.foundation.clickable
import java.util.Locale
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waterintake.domain.model.WaterLog
import com.example.waterintake.presentation.components.LiquidWaveProgress
import com.example.waterintake.presentation.theme.CyanAccent
import com.example.waterintake.presentation.theme.CyanPrimary
import com.example.waterintake.presentation.theme.PetrolCardElevated
import com.example.waterintake.presentation.theme.PetrolDarkNavy
import com.example.waterintake.presentation.theme.PetrolSurfaceDark
import com.example.waterintake.presentation.theme.TextSlateSecondary
import com.example.waterintake.presentation.theme.TextWhitePrimary
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToSettings: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.events) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is DashboardUiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = if (event.canUndo) "Geri Al" else null,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onUndo()
                    }
                }
                is DashboardUiEvent.GoalCelebration -> {
                    // Kutlama efekti & haptic
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = PetrolDarkNavy
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Başlık & Tarih
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AquaTrack",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextWhitePrimary
                        )
                    )
                    Text(
                        text = "Günlük Hidrasyon Durumu",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextSlateSecondary)
                    )
                }

                if (state.isGoalReached) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(CyanPrimary.copy(alpha = 0.2f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Hedef Tamamlandı! ✨", color = CyanAccent, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Dairesel Sıvı Dalga Göstergesi
            LiquidWaveProgress(
                progress = state.progressRatio,
                size = 250.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${state.currentIntakeMl}",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "/ ${state.dailyGoalMl} ml",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSlateSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "%${(state.progressRatio * 100).toInt()}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = if (state.remainingMl > 0) "Kalan: ${state.remainingMl} ml" else "Harika! Hedefinizi aştınız",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = if (state.remainingMl > 0) TextSlateSecondary else CyanAccent,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Hızlı Ekleme Butonları (250ml, 500ml, 750ml)
            Text(
                text = "Hızlı Su Ekle",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = TextSlateSecondary,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickAddButton(
                    amount = 250,
                    label = "Bardak",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onAddWater(250, "glass") }
                )
                QuickAddButton(
                    amount = 500,
                    label = "Şişe",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onAddWater(500, "bottle") }
                )
                QuickAddButton(
                    amount = 750,
                    label = "Termos",
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.onAddWater(750, "flask") }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Saatlik Dağılım
            HourlyDistributionSection(logs = state.recentLogs)

            Spacer(modifier = Modifier.height(28.dp))

            // Bugünkü Kayıtlar
            TodayRecordsSection(
                logs = state.recentLogs,
                onUndo = { viewModel.onUndo() }
            )
            
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
fun HourlyDistributionSection(logs: List<WaterLog>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PetrolSurfaceDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Schedule, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "GÜNÜN SAATLİK DAĞILIMI",
                        style = MaterialTheme.typography.titleSmall.copy(color = TextWhitePrimary, fontWeight = FontWeight.Bold)
                    )
                }
                Text(
                    text = "07:00 - 23:00",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextSlateSecondary)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            val hours = listOf(7, 9, 11, 13, 15, 17, 19, 21, 23)
            // Group by hour. We'll map each log to the closest interval (e.g. 8 goes to 7 or 9? Let's just group by exact interval)
            // Simple approach: map hour to the corresponding bucket
            val intakeAmounts = IntArray(hours.size) { 0 }
            logs.forEach { log ->
                val hour = Instant.ofEpochMilli(log.timestamp).atZone(ZoneId.systemDefault()).hour
                // Find closest bucket
                var closestIndex = 0
                var minDiff = Int.MAX_VALUE
                for (i in hours.indices) {
                    val diff = Math.abs(hours[i] - hour)
                    if (diff < minDiff) {
                        minDiff = diff
                        closestIndex = i
                    }
                }
                intakeAmounts[closestIndex] += log.amountMl
            }

            val maxIntake = intakeAmounts.maxOrNull()?.coerceAtLeast(1) ?: 1

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                hours.forEachIndexed { index, hour ->
                    val intake = intakeAmounts[index]
                    val heightRatio = (intake.toFloat() / maxIntake.toFloat()).coerceIn(0.1f, 1f)
                    val barHeight = if (intake > 0) (heightRatio * 50).dp else 8.dp

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .width(32.dp)
                                .height(50.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(barHeight)
                                    .background(
                                        color = if (intake > 0) CyanAccent else PetrolCardElevated,
                                        shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = String.format(Locale.getDefault(), "%02d", hour),
                            fontSize = 11.sp,
                            color = TextSlateSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TodayRecordsSection(logs: List<WaterLog>, onUndo: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BUGÜNKÜ KAYITLAR (${logs.size})",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = TextSlateSecondary,
                    fontWeight = FontWeight.Bold
                )
            )
            if (logs.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color.Transparent).clickable { onUndo() }.padding(4.dp)
                ) {
                    Icon(Icons.AutoMirrored.Rounded.Undo, contentDescription = null, tint = TextSlateSecondary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Sonuncuyu Geri Al",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSlateSecondary)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (logs.isEmpty()) {
            Text(
                text = "Henüz su içmediniz.",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSlateSecondary),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            val formatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
            // Assuming the latest logs are at the end, so we reverse
            logs.reversed().forEach { log ->
                val timeString = Instant.ofEpochMilli(log.timestamp).atZone(ZoneId.systemDefault()).format(formatter)
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PetrolSurfaceDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(PetrolCardElevated, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Rounded.LocalDrink, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "+${log.amountMl} ml",
                                    style = MaterialTheme.typography.titleMedium.copy(color = TextWhitePrimary, fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = when (log.containerType) {
                                        "glass" -> "Bardak"
                                        "bottle" -> "Şişe"
                                        "flask" -> "Termos"
                                        else -> "Diğer"
                                    },
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextSlateSecondary)
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = timeString,
                                style = MaterialTheme.typography.bodyMedium.copy(color = TextSlateSecondary)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            // Normally a delete button, maybe calls a function on click. For now it triggers onUndo if it's the last one, or maybe it deletes specific.
                            // The user's image shows a trash icon on each log. But we only have undo the last one.
                            // So let's just make it call onUndo for now, or just act as a dummy.
                            IconButton(
                                onClick = onUndo,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Rounded.Delete, contentDescription = "Sil", tint = TextSlateSecondary, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuickAddButton(
    amount: Int,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(96.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PetrolSurfaceDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Rounded.LocalDrink,
                contentDescription = null,
                tint = CyanAccent,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "+${amount}ml",
                fontWeight = FontWeight.Bold,
                color = TextWhitePrimary,
                fontSize = 15.sp
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = TextSlateSecondary
            )
        }
    }
}
