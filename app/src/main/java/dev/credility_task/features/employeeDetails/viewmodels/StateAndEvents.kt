package dev.credility_task.features.employeeDetails.viewmodels

import dev.models.ErrorResponse
import dev.models.FullEmployeeData

data class EmployeeDetailsState(
    val employee: FullEmployeeData? = null,
    val loading: Boolean = false,
    val failure: ErrorResponse? = null
)

sealed class EmployeesDetailsEvents {
    data class GetEmployeeById(val id: Int) : EmployeesDetailsEvents()
    data object ClearFailure : EmployeesDetailsEvents()
}

