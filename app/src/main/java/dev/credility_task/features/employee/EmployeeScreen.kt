package dev.credility_task.features.employee


import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.credility_task.features.employee.components.EmployeesScreenListShimmerEffect
import dev.credility_task.features.employee.components.EmptyErrorScreen
import dev.credility_task.features.employee.components.SearchBarSection
import dev.credility_task.features.employee.viewmodels.EmployeesEvents
import dev.credility_task.features.employee.viewmodels.EmployeesStates
import dev.credility_task.features.employee.viewmodels.EmployeesViewModel
import dev.designsystem.components.CustomConfirmationDialog
import dev.designsystem.components.CustomTopBar
import dev.designsystem.components.LoadingDialog
import dev.designsystem.components.uriToBitmap
import dev.designsystem.theme.CredilityTaskTheme
import dev.designsystem.theme.spacing
import dev.models.BankInfo
import dev.models.EmployeeInfo
import dev.models.FullEmployeeData
import dev.models.PersonalInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun EmployeesScreen(
    viewModel: EmployeesViewModel = hiltViewModel(),
    onAddEmployeeClick: () -> Unit,
    onEmployeeClick: (id: Int) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.employeesEvents(EmployeesEvents.GetEmployees)
    }
    val activity = LocalActivity.current
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var showLogoutDialog by remember { mutableStateOf(false) }

    BackHandler {
        showLogoutDialog = true
    }
    EmployeesScreenContent(
        state = state,
        onBackClick = {
            showLogoutDialog = true
        },
        onAddEmployeeClick = { onAddEmployeeClick.invoke() },
        events = viewModel::employeesEvents,
        onEmployeeClick = { onEmployeeClick.invoke(it) }
    )
    if (showLogoutDialog) {
        CustomConfirmationDialog(
            icon = Icons.AutoMirrored.Filled.Logout,
            title = "Oh no! You’re leaving...",
            message = "Are you sure? Please don't go",
            confirmButtonText = "Yes, Kick me Out",
            dismissButtonText = "Nah, Just Kidding",
            onConfirm = {
                showLogoutDialog = false
                if (activity?.isTaskRoot == true) {
                    activity.finishAndRemoveTask()
                }
            },
            onDismiss = { showLogoutDialog = false }
        )
    }
}


@Composable
fun EmployeesScreenContent(
    state: EmployeesStates,
    events: (EmployeesEvents) -> Unit,
    onBackClick: () -> Unit,
    onAddEmployeeClick: () -> Unit,
    onEmployeeClick: (id: Int) -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                onBackClick = onBackClick,
                title = {
                    Text(
                        text = "Employees",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddEmployeeClick) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = null
                )
            }
        },
    ) { paddingValues ->
        when {
            state.loading -> {
                EmployeesScreenListShimmerEffect(paddingValues)
            }

            state.failure != null -> {
                EmptyErrorScreen(state.failure.error.toString())
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    if (state.deleteLoading) {
                        LoadingDialog(true)
                    }
                    SearchBarSection(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                        searchQuery = state.searchQuery,
                        onSearchQueryChange = { events(EmployeesEvents.SearchEmployees(it)) },
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

                    val filteredEmployees = if (state.searchQuery.isEmpty()) {
                        state.employees.sortedBy { it.employeeInfo.employeeName }
                    } else {
                        state.employees.filter {
                            it.employeeInfo.employeeName.contains(
                                state.searchQuery,
                                ignoreCase = true
                            )
                        }.sortedBy { it.employeeInfo.employeeName }
                    }

                    if (filteredEmployees.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No employees found",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    } else {
                        LazyColumn {
                            items(filteredEmployees, key = { it.hashCode() }) { employee ->
                                EmployeeListItem(
                                    employee = employee,
                                    onClick = { onEmployeeClick(it) },
                                    onDeleteClick = { id -> events(EmployeesEvents.DeleteEmployee(id)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmployeeListItem(
    employee: FullEmployeeData,
    onClick: (id: Int) -> Unit,
    onDeleteClick: (id: Int) -> Unit,
) {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(employee.imageUri) {
        if (!employee.imageUri.isNullOrEmpty()) {
            try {
                val bitmap = withContext(Dispatchers.IO) {
                    uriToBitmap(context, employee.imageUri!!.toUri())
                }
                imageBitmap = bitmap
            } catch (e: Exception) {
                Log.e("EmployeeListItem", "Error loading image: ${e.message}")
                imageBitmap = null
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick.invoke(employee.id ?: 0) },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap!!.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = MaterialTheme.spacing.small)
                        .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(MaterialTheme.spacing.small)
                        ),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.BrokenImage,
                    contentDescription = "No Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                        .background(Color.Gray.copy(alpha = 0.1f))
                        .padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = employee.employeeInfo.employeeName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        IconButton(onClick = { onDeleteClick.invoke(employee.id ?: 0) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Employee",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    Text(
                        text = employee.employeeInfo.designation,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "ID: ${employee.employeeInfo.employeeNo} | Exp: ${employee.employeeInfo.experience}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Phone: ${employee.personalInfo.phone} | Gender: ${employee.personalInfo.gender}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "DOB: ${employee.personalInfo.dob}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }

}


@PreviewLightDark
@Composable
fun EmployeeListItemPreview() {
    val dummyEmployees = listOf(
        createDummyEmployeeData(
            firstName = "John",
            lastName = "Doe",
            phone = "123-456-7890",
            gender = "Male",
            dob = "01/01/1990",
            employeeNo = "EMP001",
            employeeName = "John Doe",
            designation = "Software Engineer",
            accountType = "Savings",
            experience = "5 years",
            bankName = "XYZ Bank",
            branchName = "Main Branch",
            accountNo = "1234567890",
            ifscCode = "XYZ0000001",
            id = 1
        ),
        createDummyEmployeeData(
            firstName = "Jane",
            lastName = "Smith",
            phone = "987-654-3210",
            gender = "Female",
            dob = "05/15/1985",
            employeeNo = "EMP002",
            employeeName = "Jane Smith",
            designation = "Product Manager",
            accountType = "Current",
            experience = "8 years",
            bankName = "ABC Bank",
            branchName = "Downtown Branch",
            accountNo = "0987654321",
            ifscCode = "ABC0000002",
            id = 2
        ),
        createDummyEmployeeData(
            firstName = "David",
            lastName = "Lee",
            phone = "555-123-4567",
            gender = "Male",
            dob = "11/22/1992",
            employeeNo = "EMP003",
            employeeName = "David Lee",
            designation = "UI/UX Designer",
            accountType = "Savings",
            experience = "3 years",
            bankName = "PQR Bank",
            branchName = "Uptown Branch",
            accountNo = "5678901234",
            ifscCode = "PQR0000003",
            id = 3
        ),
        createDummyEmployeeData(
            firstName = "Alice",
            lastName = "Johnson",
            phone = "111-222-3333",
            gender = "Female",
            dob = "03/10/1988",
            employeeNo = "EMP004",
            employeeName = "Alice Johnson",
            designation = "Data Analyst",
            accountType = "Current",
            experience = "6 years",
            bankName = "LMN Bank",
            branchName = "Midtown Branch",
            accountNo = "4321098765",
            ifscCode = "LMN0000004",
            id = 4
        ),
    )

    CredilityTaskTheme {
        EmployeesScreenContent(
            state = EmployeesStates(employees = dummyEmployees),
            events = {},
            onBackClick = {},
            onAddEmployeeClick = {},
            onEmployeeClick = {}
        )
    }
}

fun createDummyEmployeeData(
    firstName: String,
    lastName: String,
    phone: String,
    gender: String,
    dob: String,
    employeeNo: String,
    employeeName: String,
    designation: String,
    accountType: String,
    experience: String,
    bankName: String,
    branchName: String,
    accountNo: String,
    ifscCode: String,
    imageUri: String? = null,
    id: Int? = null
): FullEmployeeData {
    return FullEmployeeData(
        id = id,
        personalInfo = PersonalInfo(
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            gender = gender,
            dob = dob
        ),
        employeeInfo = EmployeeInfo(
            employeeNo = employeeNo,
            employeeName = employeeName,
            designation = designation,
            accountType = accountType,
            experience = experience
        ),
        bankInfo = BankInfo(
            bankName = bankName,
            branchName = branchName,
            accountNo = accountNo,
            ifscCode = ifscCode
        ),
        imageUri = imageUri
    )
}