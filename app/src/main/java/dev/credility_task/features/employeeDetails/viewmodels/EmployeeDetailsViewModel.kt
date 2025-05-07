package dev.credility_task.features.employeeDetails.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.data.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EmployeeDetailsState())
    val state = _state.asStateFlow()

    fun employeesEvents(event: EmployeesDetailsEvents) {
        when (event) {
            EmployeesDetailsEvents.ClearFailure -> {
                _state.update { it.copy(failure = null) }
            }

            is EmployeesDetailsEvents.GetEmployeeById -> {
                getEmployeeById(event.id)
            }
        }
    }

    private fun getEmployeeById(id: Int) {
        _state.update { it.copy(loading = true, failure = null) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEmployeeById(id).onRight { employeeData ->
                _state.update { it ->
                    it.copy(
                        employee = employeeData,
                        loading = false
                    )
                }
            }.onLeft { failure ->
                _state.update { it.copy(failure = failure, loading = false) }
            }
        }
    }
}
