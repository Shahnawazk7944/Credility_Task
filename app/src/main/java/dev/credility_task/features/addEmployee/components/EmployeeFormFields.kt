package dev.credility_task.features.addEmployee.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.credility_task.features.addEmployee.viewmodels.BankInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.BankInfoStates
import dev.credility_task.features.addEmployee.viewmodels.EmployeeInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.EmployeeInfoStates
import dev.credility_task.features.addEmployee.viewmodels.PersonalInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.PersonalInfoStates
import dev.designsystem.components.OutlinedDateInputField
import dev.designsystem.components.OutlinedInputField
import dev.designsystem.components.uriToBitmap
import dev.designsystem.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PersonalInfoInputFields(
    state: PersonalInfoStates,
    events: (PersonalInfoEvents) -> Unit
) {
    var genderTypeExpanded by remember { mutableStateOf(false) }

    Column {
        OutlinedInputField(
            value = state.firstName,
            onChange = {
                events(PersonalInfoEvents.FirstNameChanged(it))
            },
            label = "First Name",
            placeholder = { Text(text = "Alex", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Badge,
                    contentDescription = "Id icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.firstNameError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        OutlinedInputField(
            value = state.lastName,
            onChange = {
                events(PersonalInfoEvents.LastNameChanged(it))
            },
            label = "Last Name",
            placeholder = { Text(text = "Martin", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Badge,
                    contentDescription = "Id icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.lastNameError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        OutlinedInputField(
            value = state.phoneNumber,
            onChange = {
                events(PersonalInfoEvents.PhoneNumberChanged(it))
            },
            label = "Phone Number",
            placeholder = {
                Text(
                    text = "+91 1234 1234",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Phone icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.phoneNumberError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedInputField(
                value = state.gender,
                onChange = {},
                label = "Gender",
                placeholder = {
                    Text(
                        text = "Male",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) { detectTapGestures { genderTypeExpanded = true } },
                leadingIcon = {
                    Icon(
                        Icons.Default.Wc,
                        contentDescription = "Gender icon",
                        modifier = Modifier.size(20.dp)
                    )
                },
                error = state.genderError,
                maxLines = 1
            )

            DropdownMenu(
                expanded = genderTypeExpanded,
                onDismissRequest = { genderTypeExpanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                listOf("Male", "Female", "Others").forEach { type ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = type,
                                style = MaterialTheme.typography.titleMedium,
                                color = colorScheme.tertiary
                            )
                        },
                        onClick = {
                            genderTypeExpanded = false
                            events(PersonalInfoEvents.GenderChanged(type))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = when (type) {
                                    "Male" -> Icons.Default.Man
                                    "Female" -> Icons.Default.Woman
                                    else -> Icons.Default.Diversity1
                                },
                                tint = colorScheme.primary,
                                contentDescription = type
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        OutlinedDateInputField(
            label = "Date of Birth",
            date = state.dob,
            onDateChange = {
                events(PersonalInfoEvents.DobChanged(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "20-05-2025",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.CalendarMonth,
                    contentDescription = "Calendar icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.dobError,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
    }
}

@Composable
fun EmployeeInfoInputFields(
    state: EmployeeInfoStates,
    events: (EmployeeInfoEvents) -> Unit
) {
    var accountTypeExpanded by remember { mutableStateOf(false) }
    var workExpExpanded by remember { mutableStateOf(false) }

    Column {
        OutlinedInputField(
            value = state.employeeNumber,
            onChange = {
                events(EmployeeInfoEvents.EmployeeNumberChanged(it))
            },
            label = "Employee Number",
            placeholder = { Text(text = "123654", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            leadingIcon = {
                Icon(
                    Icons.Default.Numbers,
                    contentDescription = "Number icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.employeeNumberError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        OutlinedInputField(
            value = state.employeeName,
            onChange = {
                events(EmployeeInfoEvents.EmployeeNameChanged(it))
            },
            label = "Employee Name",
            placeholder = { Text(text = "Alex Zam", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Person icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.employeeNameError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        OutlinedInputField(
            value = state.designation,
            onChange = {
                events(EmployeeInfoEvents.DesignationChanged(it))
            },
            label = "Designation",
            placeholder = {
                Text(
                    text = "Software Engineer",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Description,
                    contentDescription = "Designation icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.designationError,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedInputField(
                value = state.accountType,
                onChange = { events(EmployeeInfoEvents.AccountTypeChanged(it)) },
                label = "Account Type",
                placeholder = {
                    Text(
                        text = "Saving",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) { detectTapGestures { accountTypeExpanded = true } },
                leadingIcon = {
                    Icon(
                        Icons.Default.Category,
                        contentDescription = "Category icon",
                        modifier = Modifier.size(20.dp)
                    )
                },
                error = state.accountTypeError,
                maxLines = 1
            )

            DropdownMenu(
                expanded = accountTypeExpanded,
                onDismissRequest = { accountTypeExpanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                listOf("Saving", "Current", "Others").forEach { type ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = type,
                                style = MaterialTheme.typography.titleMedium,
                                color = colorScheme.tertiary
                            )
                        },
                        onClick = {
                            accountTypeExpanded = false
                            events(EmployeeInfoEvents.AccountTypeChanged(type))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = when (type) {
                                    "Saving" -> Icons.Default.Savings
                                    "Current" -> Icons.Default.PriceCheck

                                    else -> Icons.Default.DynamicFeed
                                },
                                tint = colorScheme.primary,
                                contentDescription = type
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedInputField(
                value = state.workExperience,
                onChange = { events(EmployeeInfoEvents.WorkExperienceChanged(it)) },
                label = "Work Experience",
                placeholder = {
                    Text(
                        text = "4 Years",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) { detectTapGestures { workExpExpanded = true } },
                leadingIcon = {
                    Icon(
                        Icons.Default.DynamicFeed,
                        contentDescription = "Category icon",
                        modifier = Modifier.size(20.dp)
                    )
                },
                error = state.workExperienceError,
                maxLines = 1
            )

            DropdownMenu(
                expanded = workExpExpanded,
                onDismissRequest = { workExpExpanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                listOf("1 Year", "2 Years", "3 Years", "4+ Years").forEach { type ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = type,
                                style = MaterialTheme.typography.titleMedium,
                                color = colorScheme.tertiary
                            )
                        },
                        onClick = {
                            workExpExpanded = false
                            events(EmployeeInfoEvents.WorkExperienceChanged(type))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = when (type) {
                                    "1 Year" -> Icons.Default.Timeline
                                    "2 Years" -> Icons.Default.Timeline
                                    "3 Years" -> Icons.Default.Timeline
                                    else -> Icons.Default.Timer
                                },
                                tint = colorScheme.primary,
                                contentDescription = type
                            )
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
    }
}

@Composable
fun BankInfoInputFields(
    state: BankInfoStates,
    events: (BankInfoEvents) -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            events(BankInfoEvents.ImageChanged(uri))
            CoroutineScope(Dispatchers.IO).launch {
                val bitmap = uriToBitmap(context = context, uri = uri)
                bitmap?.let {
                    events(BankInfoEvents.ImageBitmapChanged(it))
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (state.imageBitmap != null) {
                Image(
                    bitmap = state.imageBitmap.asImageBitmap(),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(end = MaterialTheme.spacing.small)
                        .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                        .border(
                            1.dp,
                            colorScheme.primary,
                            RoundedCornerShape(MaterialTheme.spacing.small)
                        )
                        .clickable {
                            launcher.launch(arrayOf("image/*"))
                        },
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = colorScheme.primary,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(end = MaterialTheme.spacing.small)
                            .clip(RoundedCornerShape(MaterialTheme.spacing.small))
                            .border(
                                2.dp,
                                colorScheme.primary,
                                RoundedCornerShape(MaterialTheme.spacing.small)
                            )
                            .clickable {
                                launcher.launch(arrayOf("image/*"))
                            }
                            .padding(MaterialTheme.spacing.medium)
                    )
                    Text(
                        text = "Please select document",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (state.imageUriError != null) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        OutlinedInputField(
            value = state.bankName,
            onChange = {
                events(BankInfoEvents.BankNameChanged(it))
            },
            label = "Bank Name",
            placeholder = { Text(text = "HDFC Bank", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.FoodBank,
                    contentDescription = "Bank icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.bankNameError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        OutlinedInputField(
            value = state.bankBranchName,
            onChange = {
                events(BankInfoEvents.BankBranchNameChanged(it))
            },
            label = "Branch Name",
            placeholder = { Text(text = "Andheri", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Description,
                    contentDescription = "Address icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.bankBranchNameError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

        OutlinedInputField(
            value = state.accountNumber,
            onChange = {
                events(BankInfoEvents.AccountNumberChanged(it))
            },
            label = "Account Number",
            placeholder = {
                Text(
                    text = "12462485525",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.ConfirmationNumber,
                    contentDescription = "Bank icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.accountNumberError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        OutlinedInputField(
            value = state.ifscCode,
            onChange = {
                events(BankInfoEvents.IfscCodeChanged(it))
            },
            label = "IFSC Code",
            placeholder = {
                Text(
                    text = "AND0002344",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Code,
                    contentDescription = "Address icon",
                    modifier = Modifier.size(20.dp)
                )
            },
            error = state.ifscCodeError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
    }
}