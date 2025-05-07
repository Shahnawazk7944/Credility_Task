import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
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
import dev.credility_task.features.employee.components.EmptyErrorScreen
import dev.credility_task.features.employee.createDummyEmployeeData
import dev.credility_task.features.employeeDetails.viewmodels.EmployeeDetailsViewModel
import dev.credility_task.features.employeeDetails.viewmodels.EmployeesDetailsEvents
import dev.designsystem.components.CustomTopBar
import dev.designsystem.components.LoadingDialog
import dev.designsystem.components.uriToBitmap
import dev.designsystem.theme.CredilityTaskTheme
import dev.designsystem.theme.spacing
import dev.models.FullEmployeeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun EmployeeDetailsScreen(
    employeeId: Int,
    viewModel: EmployeeDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = Unit) {
        viewModel.employeesEvents(EmployeesDetailsEvents.GetEmployeeById(id = employeeId))
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                onBackClick = onBackClick,
                title = {
                    Text(
                        text = "Employee Details",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
                },
            )
        }
    ) { paddingValues ->
        when {
            state.loading -> {
                LoadingDialog(true)
            }

            state.failure != null -> {
                EmptyErrorScreen(state.failure.error.toString())
            }

            state.employee == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Employee not found", style = MaterialTheme.typography.titleMedium)
                }
            }

            else -> {
                EmployeeDetailContent(state.employee, paddingValues)
            }
        }
    }
}

@Composable
private fun EmployeeDetailContent(
    employee: FullEmployeeData,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = MaterialTheme.spacing.medium)
    ) {
        item {
            EmployeeImageSection(employee.imageUri)
            SectionHeader("Personal Info")
            InfoRow("Name", "${employee.personalInfo.firstName} ${employee.personalInfo.lastName}")
            InfoRow("Phone", employee.personalInfo.phone)
            InfoRow("Gender", employee.personalInfo.gender)
            InfoRow("Date of Birth", employee.personalInfo.dob)

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            SectionHeader("Employee Info")
            InfoRow("Employee No", employee.employeeInfo.employeeNo)
            InfoRow("Designation", employee.employeeInfo.designation)
            InfoRow("Account Type", employee.employeeInfo.accountType)
            InfoRow("Experience", employee.employeeInfo.experience)

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            SectionHeader("Bank Info")
            InfoRow("Bank Name", employee.bankInfo.bankName)
            InfoRow("Branch Name", employee.bankInfo.branchName)
            InfoRow("Account No", employee.bankInfo.accountNo)
            InfoRow("IFSC Code", employee.bankInfo.ifscCode)

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = MaterialTheme.spacing.small)
    )
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
fun EmployeeImageSection(imageUri: String?) {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(imageUri) {
        if (!imageUri.isNullOrEmpty()) {
            try {
                val bitmap = withContext(Dispatchers.IO) {
                    uriToBitmap(context, imageUri.toUri())
                }
                imageBitmap = bitmap
            } catch (e: Exception) {
                Log.e("EmployeeImageSection", "Error loading image: ${e.message}")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.medium),
        contentAlignment = Alignment.Center
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap!!.asImageBitmap(),
                contentDescription = "Employee Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "No Image",
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray.copy(alpha = 0.2f), CircleShape)
                    .padding(16.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun EmployeeListItemPreview() {
    val dummyEmployee =
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
        )
    CredilityTaskTheme {
        EmployeeDetailContent(
            employee = dummyEmployee,
            padding = PaddingValues(0.dp)
        )
    }
}