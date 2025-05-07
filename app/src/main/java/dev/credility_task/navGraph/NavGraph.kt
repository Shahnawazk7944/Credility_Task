package dev.credility_task.navGraph

import EmployeeDetailsScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.credility_task.features.addEmployee.AddEmployeeScreen
import dev.credility_task.features.employee.EmployeesScreen


@Composable
fun CredilityTaskNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        navController = navController,
        startDestination = Routes.EmployeesScreen
    ) {
        composable<Routes.EmployeesScreen> {
            EmployeesScreen(
                onAddEmployeeClick = {
                    navController.navigate(Routes.AddEmployeeScreen)
                },
                onEmployeeClick = { navController.navigate(Routes.EmployeeDetailsScreen(it)) }
            )
        }
        composable<Routes.AddEmployeeScreen> {
            AddEmployeeScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
        composable<Routes.EmployeeDetailsScreen> { navBackStackEntry ->
            val employee: Routes.EmployeeDetailsScreen = navBackStackEntry.toRoute()
            EmployeeDetailsScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                employeeId = employee.id,
            )
        }
    }
}
