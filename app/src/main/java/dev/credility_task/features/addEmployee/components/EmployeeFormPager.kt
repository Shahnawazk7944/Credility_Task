package dev.credility_task.features.addEmployee.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.credility_task.features.addEmployee.viewmodels.BankInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.BankInfoStates
import dev.credility_task.features.addEmployee.viewmodels.EmployeeInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.EmployeeInfoStates
import dev.credility_task.features.addEmployee.viewmodels.PersonalInfoEvents
import dev.credility_task.features.addEmployee.viewmodels.PersonalInfoStates
import dev.credility_task.features.utils.isFormValid
import dev.credility_task.features.utils.toBankInfo
import dev.credility_task.features.utils.toEmployeeInfo
import dev.credility_task.features.utils.toPersonalInfo
import dev.designsystem.components.PrimaryButton
import dev.designsystem.components.SecondaryButton
import dev.models.FullEmployeeData
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmployeeFormPager(
    pagerState: PagerState,
    personalInfoState: PersonalInfoStates,
    employeeInfoState: EmployeeInfoStates,
    bankInfoState: BankInfoStates,
    personalInfoEvents: (PersonalInfoEvents) -> Unit,
    employeeInfoEvents: (EmployeeInfoEvents) -> Unit,
    bankInfoEvents: (BankInfoEvents) -> Unit,
    onSubmit: (FullEmployeeData) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val isCurrentPageValid = when (pagerState.currentPage) {
        0 -> personalInfoState.isFormValid()
        1 -> employeeInfoState.isFormValid()
        2 -> bankInfoState.isFormValid()
        else -> false
    }

    Column(modifier = Modifier.padding(8.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> PersonalInfoInputFields(personalInfoState, personalInfoEvents)
                1 -> EmployeeInfoInputFields(employeeInfoState, employeeInfoEvents)
                2 -> BankInfoInputFields(bankInfoState, bankInfoEvents)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (pagerState.currentPage > 0) {
                SecondaryButton(
                    label = "Back",
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            PrimaryButton(
                enabled = isCurrentPageValid,
                label = if (pagerState.currentPage < 2) "Next" else "Submit",
                onClick = {
                    if (pagerState.currentPage < 2) {
                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        val fullEmployeeData = FullEmployeeData(
                            id = 0,
                            personalInfo = personalInfoState.toPersonalInfo(),
                            employeeInfo = employeeInfoState.toEmployeeInfo(),
                            bankInfo = bankInfoState.toBankInfo(),
                            imageUri = bankInfoState.imageUri?.toString()
                        )
                        onSubmit(fullEmployeeData)
                    }
                },
                modifier = Modifier.weight(4f)
            )

        }
    }
}
