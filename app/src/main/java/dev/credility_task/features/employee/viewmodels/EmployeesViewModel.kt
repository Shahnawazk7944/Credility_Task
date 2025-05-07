package dev.credility_task.features.employee.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.data.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EmployeesStates())
    val state = _state.asStateFlow()

    fun employeesEvents(event: EmployeesEvents) {
        when (event) {
            EmployeesEvents.GetEmployees -> {
                getEmployees()
            }

            EmployeesEvents.ClearFailure -> {
                _state.update { it.copy(failure = null) }
            }

            is EmployeesEvents.SearchEmployees -> {
                _state.update { it.copy(searchQuery = event.query) }
            }

            is EmployeesEvents.DeleteEmployee -> {
                deleteEmployees(event.id)
            }
        }
    }

    private fun getEmployees() {
        _state.update { it.copy(loading = true, failure = null) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllEmployees().onRight {
                it.collect { employees ->
                    delay(1000)
                    _state.update { it ->
                        it.copy(
                            employees = employees,
                            loading = false
                        )
                    }
                }
            }.onLeft { failure ->
                _state.update { it.copy(failure = failure, loading = false) }
            }
        }
    }

    private fun deleteEmployees(id: Int) {
        _state.update { it.copy(deleteLoading = true, failure = null) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEmployee(id).onRight {
                delay(500)
                _state.update { it ->
                    it.copy(
                        deleteLoading = false
                    )
                }
            }.onLeft { failure ->
                _state.update { it.copy(failure = failure, loading = false) }
            }
        }
    }

}
