package com.example.waterintake.presentation.profile

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.waterintake.presentation.theme.CyanAccent
import com.example.waterintake.presentation.theme.CyanPrimary
import com.example.waterintake.presentation.theme.PetrolDarkNavy
import com.example.waterintake.presentation.theme.PetrolSurfaceDark
import com.example.waterintake.presentation.theme.TextSlateSecondary
import com.example.waterintake.presentation.theme.TextWhitePrimary

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val state by viewModel.uiState.collectAsState()
    val profile = state.profile

    Scaffold(
        containerColor = PetrolDarkNavy
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profil",
                    tint = CyanAccent,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Profilim",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhitePrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (profile != null) {
                var name by remember(profile) { mutableStateOf(profile.name) }
                var weight by remember(profile) { mutableStateOf(profile.weightKg.toString()) }
                var age by remember(profile) { mutableStateOf(profile.age.toString()) }
                var customGoal by remember(profile) { mutableStateOf(profile.customGoalMl.toString()) }
                var useCustomGoal by remember(profile) { mutableStateOf(profile.useCustomGoal) }

                ProfileTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Ad"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ProfileTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = "Kilo (kg)",
                    keyboardType = KeyboardType.Number
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                ProfileTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = "Yaş",
                    keyboardType = KeyboardType.Number
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Özel Günlük Su Hedefi Belirle", color = TextWhitePrimary)
                    Switch(
                        checked = useCustomGoal,
                        onCheckedChange = { useCustomGoal = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = CyanAccent,
                            checkedTrackColor = CyanPrimary.copy(alpha = 0.5f),
                            uncheckedThumbColor = TextSlateSecondary,
                            uncheckedTrackColor = PetrolSurfaceDark
                        )
                    )
                }
                
                if (useCustomGoal) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileTextField(
                        value = customGoal,
                        onValueChange = { customGoal = it },
                        label = "Özel Hedef (ml)",
                        keyboardType = KeyboardType.Number
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Otomatik Hesaplanan Hedef: ${profile.dailyGoalMl} ml",
                        color = TextSlateSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = {
                        val updatedProfile = profile.copy(
                            name = name,
                            weightKg = weight.toIntOrNull() ?: profile.weightKg,
                            age = age.toIntOrNull() ?: profile.age,
                            useCustomGoal = useCustomGoal,
                            customGoalMl = customGoal.toIntOrNull() ?: profile.customGoalMl
                        )
                        viewModel.saveProfile(updatedProfile)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                ) {
                    Text(
                        text = "Değişiklikleri Kaydet",
                        color = PetrolDarkNavy,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            } else {
                CircularProgressIndicator(
                    color = CyanAccent,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSlateSecondary) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = CyanAccent,
            unfocusedBorderColor = PetrolSurfaceDark,
            focusedTextColor = TextWhitePrimary,
            unfocusedTextColor = TextWhitePrimary,
            cursorColor = CyanAccent,
            focusedContainerColor = PetrolSurfaceDark,
            unfocusedContainerColor = PetrolSurfaceDark
        ),
        shape = RoundedCornerShape(12.dp)
    )
}
