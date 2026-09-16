package com.example.waterintake.presentation.history

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.LocalDrink
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waterintake.presentation.theme.CyanAccent
import com.example.waterintake.presentation.theme.PetrolCardElevated
import com.example.waterintake.presentation.theme.PetrolDarkNavy
import com.example.waterintake.presentation.theme.PetrolSurfaceDark
import com.example.waterintake.presentation.theme.TextSlateSecondary
import com.example.waterintake.presentation.theme.TextWhitePrimary
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = PetrolDarkNavy
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.DateRange,
                    contentDescription = "Geçmiş",
                    tint = CyanAccent,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Geçmiş Kayıtlar",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhitePrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (state.historyData.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Henüz kayıt bulunmuyor.",
                        color = TextSlateSecondary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                val sortedData = state.historyData.entries.sortedByDescending { it.key }
                val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(sortedData) { (dateStr, totalMl) ->
                        val date = try {
                            LocalDate.parse(dateStr)
                        } catch (e: Exception) {
                            null
                        }
                        
                        val displayDate = date?.format(formatter) ?: dateStr

                        HistoryItemCard(date = displayDate, totalMl = totalMl)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(date: String, totalMl: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PetrolSurfaceDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(PetrolCardElevated, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocalDrink,
                        contentDescription = null,
                        tint = CyanAccent,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = TextWhitePrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Günlük Toplam",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSlateSecondary)
                    )
                }
            }

            Text(
                text = "$totalMl ml",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = CyanAccent,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}
