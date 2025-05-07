package dev.credility_task.navGraph

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object EmployeesScreen : Routes()

    @Serializable
    data object AddEmployeeScreen : Routes()

    @Serializable
    data class EmployeeDetailsScreen(val id: Int) : Routes()
}