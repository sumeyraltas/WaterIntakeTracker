package com.example.waterintake.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waterintake.domain.model.UserProfile
import com.example.waterintake.domain.model.WaterLog
import com.example.waterintake.domain.usecase.AddWaterIntakeUseCase
import com.example.waterintake.domain.usecase.UndoWaterIntakeUseCase
import com.example.waterintake.domain.repository.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class DashboardUiState(
    val currentIntakeMl: Int = 0,
    val dailyGoalMl: Int = 2800,
    val progressRatio: Float = 0f,
    val remainingMl: Int = 2800,
    val recentLogs: List<WaterLog> = emptyList(),
    val isGoalReached: Boolean = false,
    val quickAddPresets: List<Int> = listOf(250, 500, 750)
)

sealed interface DashboardUiEvent {
    data class ShowSnackbar(val message: String, val canUndo: Boolean) : DashboardUiEvent
    object GoalCelebration : DashboardUiEvent
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: WaterRepository,
    private val addWaterIntakeUseCase: AddWaterIntakeUseCase,
    private val undoWaterIntakeUseCase: UndoWaterIntakeUseCase
) : ViewModel() {

    private val todayString: String
        get() = LocalDate.now().toString()

    private val _events = MutableSharedFlow<DashboardUiEvent>()
    val events: SharedFlow<DashboardUiEvent> = _events.asSharedFlow()

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.getTodayTotalIntake(todayString),
        repository.getUserProfile(),
        repository.getTodayLogs(todayString)
    ) { totalIntake, profile, logs ->
        val goal = profile.activeGoalMl
        val ratio = if (goal > 0) (totalIntake.toFloat() / goal).coerceIn(0f, 1.5f) else 0f
        val remaining = (goal - totalIntake).coerceAtLeast(0)
        
        DashboardUiState(
            currentIntakeMl = totalIntake,
            dailyGoalMl = goal,
            progressRatio = ratio,
            remainingMl = remaining,
            recentLogs = logs,
            isGoalReached = totalIntake >= goal
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState()
    )

    fun onAddWater(amountMl: Int, containerType: String = "glass") {
        viewModelScope.launch {
            val previousTotal = uiState.value.currentIntakeMl
            val goal = uiState.value.dailyGoalMl
            addWaterIntakeUseCase(amountMl, containerType)

            if (previousTotal < goal && (previousTotal + amountMl) >= goal) {
                _events.emit(DashboardUiEvent.GoalCelebration)
            }
            _events.emit(
                DashboardUiEvent.ShowSnackbar(
                    message = "${amountMl} ml su eklendi",
                    canUndo = true
                )
            )
        }
    }

    fun onUndo() {
        viewModelScope.launch {
            val undone = undoWaterIntakeUseCase()
            if (undone != null) {
                _events.emit(
                    DashboardUiEvent.ShowSnackbar(
                        message = "${undone.amountMl} ml geri alındı",
                        canUndo = false
                    )
                )
            }
        }
    }
}
