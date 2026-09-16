package com.example.waterintake.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintake.domain.model.UserProfile
import com.example.waterintake.domain.repository.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val profile: UserProfile? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: WaterRepository
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = repository.getUserProfile()
        .map { ProfileUiState(profile = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileUiState()
        )

    fun saveProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            repository.updateUserProfile(updatedProfile)
        }
    }
}
