package dev.credility_task.features.employee.viewmodels

import dev.models.ErrorResponse
import dev.models.FullEmployeeData

data class EmployeesStates(
    val loading: Boolean = false,
    val deleteLoading: Boolean = false,
    val failure: ErrorResponse? = null,
    val employees: List<FullEmployeeData> = emptyList(),
    val searchQuery: String = ""
)

sealed class EmployeesEvents {
    data object GetEmployees : EmployeesEvents()
    data object ClearFailure : EmployeesEvents()
    data class SearchEmployees(val query: String) : EmployeesEvents()
    data class DeleteEmployee(val id: Int) : EmployeesEvents()
}

