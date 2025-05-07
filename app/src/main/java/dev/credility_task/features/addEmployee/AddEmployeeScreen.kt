package dev.credility_task.features.addEmployee


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.credility_task.features.addEmployee.components.EmployeeFormPager
import dev.credility_task.features.addEmployee.components.FormPagerProgressIndicator
import dev.credility_task.features.addEmployee.viewmodels.AddEmployeeEvents
import dev.credility_task.features.addEmployee.viewmodels.AddEmployeeStates
import dev.credility_task.features.addEmployee.viewmodels.AddEmployeeViewModel
import dev.credility_task.features.addEmployee.viewmodels.BankInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.BankInfoStates
import dev.credility_task.features.addEmployee.viewmodels.EmployeeInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.EmployeeInfoStates
import dev.credility_task.features.addEmployee.viewmodels.PersonalInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.PersonalInfoStates
import dev.designsystem.components.CustomTopBar
import dev.designsystem.components.LoadingDialog
import dev.designsystem.theme.CredilityTaskTheme
import dev.designsystem.theme.spacing
import dev.models.FullEmployeeData

@Composable
fun AddEmployeeScreen(
    viewModel: AddEmployeeViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val personalInfoState by viewModel.personalInfoState.collectAsStateWithLifecycle()
    val employeeInfoState by viewModel.employeeInfoState.collectAsStateWithLifecycle()
    val bankInfoState by viewModel.bankInfoState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = state.isEmployeeAdded) {
        state.isEmployeeAdded?.let {
            Toast.makeText(context, state.isEmployeeAdded, Toast.LENGTH_SHORT).show()
            onBackClick.invoke()
        }
    }
    LaunchedEffect(key1 = state.failure) {
        state.failure?.let {
            snackbarHostState.showSnackbar(
                message = state.failure?.error ?: "Unknown error",
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
            viewModel.addEmployeeEvents(AddEmployeeEvents.ClearFailure)
        }
    }

    AddProductScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onBackClick = {
            onBackClick.invoke()
        },
        personalInfoState = personalInfoState,
        employeeInfoState = employeeInfoState,
        bankInfoState = bankInfoState,
        personalInfoEvents = viewModel::personalInfoEvents,
        employeeInfoEvents = viewModel::employeeInfoEvents,
        bankInfoEvents = viewModel::bankInfoEvents,
        onSubmit = { fullEmployeeData ->
            viewModel.addEmployeeEvents(
                AddEmployeeEvents.AddEmployee(
                    fullEmployeeData
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreenContent(
    state: AddEmployeeStates,
    personalInfoState: PersonalInfoStates,
    employeeInfoState: EmployeeInfoStates,
    bankInfoState: BankInfoStates,
    personalInfoEvents: (PersonalInfoEvents) -> Unit,
    employeeInfoEvents: (EmployeeInfoEvents) -> Unit,
    bankInfoEvents: (BankInfoEvents) -> Unit,
    snackbarHostState: SnackbarHostState,
    onSubmit: (FullEmployeeData) -> Unit,
    onBackClick: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Scaffold(
        topBar = {
            CustomTopBar(
                onBackClick = onBackClick,
                title = {
                    Text(
                        text = "Add Employee",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
                },
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            ) {
                Snackbar(
                    containerColor = colorScheme.error,
                    contentColor = colorScheme.onError,
                    snackbarData = it,
                    actionColor = colorScheme.secondary,
                    dismissActionContentColor = colorScheme.secondary
                )
            }
        },
    ) { paddingValues ->
        if (state.loading) {
            LoadingDialog(true)
        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = MaterialTheme.spacing.medium)
                .verticalScroll(rememberScrollState())

        ) {
            FormPagerProgressIndicator(pagerState.currentPage)
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            EmployeeFormPager(
                pagerState = pagerState,
                personalInfoState = personalInfoState,
                employeeInfoState = employeeInfoState,
                bankInfoState = bankInfoState,
                personalInfoEvents = personalInfoEvents,
                employeeInfoEvents = employeeInfoEvents,
                bankInfoEvents = bankInfoEvents,
                onSubmit = { fullEmployeeData -> onSubmit(fullEmployeeData) }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun AddEmployeeScreenContentPreview() {
    CredilityTaskTheme {
        AddProductScreenContent(
            state = AddEmployeeStates(),
            snackbarHostState = SnackbarHostState(),
            onBackClick = {},
            personalInfoState = PersonalInfoStates(),
            employeeInfoState = EmployeeInfoStates(),
            bankInfoState = BankInfoStates(),
            personalInfoEvents = {},
            employeeInfoEvents = {},
            bankInfoEvents = {},
            onSubmit = {},
        )
    }
}

