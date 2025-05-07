package dev.credility_task.features.addEmployee.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.credility_task.features.utils.Validator
import dev.data.repository.EmployeeRepository
import dev.models.FullEmployeeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(
    private val repository: EmployeeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AddEmployeeStates())
    val state = _state.asStateFlow()
    private val _personalInfoState = MutableStateFlow(PersonalInfoStates())
    val personalInfoState = _personalInfoState.asStateFlow()
    private val _employeeInfoState = MutableStateFlow(EmployeeInfoStates())
    val employeeInfoState = _employeeInfoState.asStateFlow()
    private val _bankInfoState = MutableStateFlow(BankInfoStates())
    val bankInfoState = _bankInfoState.asStateFlow()

    fun personalInfoEvents(event: PersonalInfoEvents) {
        when (event) {
            is PersonalInfoEvents.FirstNameChanged -> {
                val error = Validator.validateFirstName(event.firstName)
                _personalInfoState.update {
                    it.copy(
                        firstName = event.firstName,
                        firstNameError = error?.message
                    )
                }
            }

            is PersonalInfoEvents.LastNameChanged -> {
                val error = Validator.validateLastName(event.lastName)
                _personalInfoState.update {
                    it.copy(
                        lastName = event.lastName,
                        lastNameError = error?.message
                    )
                }
            }

            is PersonalInfoEvents.PhoneNumberChanged -> {
                val error = Validator.validatePhoneNumber(event.phoneNumber)
                _personalInfoState.update {
                    it.copy(
                        phoneNumber = event.phoneNumber,
                        phoneNumberError = error?.message
                    )
                }
            }

            is PersonalInfoEvents.GenderChanged -> {
                val error = Validator.validateGender(event.gender)
                _personalInfoState.update {
                    it.copy(
                        gender = event.gender,
                        genderError = error?.message
                    )
                }
            }

            is PersonalInfoEvents.DobChanged -> {
                val error = Validator.validateDob(event.dob)
                _personalInfoState.update {
                    it.copy(
                        dob = event.dob,
                        dobError = error?.message
                    )
                }
            }
        }
    }

    fun employeeInfoEvents(event: EmployeeInfoEvents) {
        when (event) {
            is EmployeeInfoEvents.EmployeeNumberChanged -> {
                val error = Validator.validateEmployeeNumber(event.employeeNumber)
                _employeeInfoState.update {
                    it.copy(
                        employeeNumber = event.employeeNumber,
                        employeeNumberError = error?.message
                    )
                }
            }

            is EmployeeInfoEvents.EmployeeNameChanged -> {
                val error = Validator.validateEmployeeName(event.employeeName)
                _employeeInfoState.update {
                    it.copy(
                        employeeName = event.employeeName,
                        employeeNameError = error?.message
                    )
                }
            }

            is EmployeeInfoEvents.DesignationChanged -> {
                val error = Validator.validateDesignation(event.designation)
                _employeeInfoState.update {
                    it.copy(
                        designation = event.designation,
                        designationError = error?.message
                    )
                }
            }

            is EmployeeInfoEvents.AccountTypeChanged -> {
                val error = Validator.validateAccountType(event.accountType)
                _employeeInfoState.update {
                    it.copy(
                        accountType = event.accountType,
                        accountTypeError = error?.message
                    )
                }
            }

            is EmployeeInfoEvents.WorkExperienceChanged -> {
                val error = Validator.validateWorkExperience(event.workExperience)
                _employeeInfoState.update {
                    it.copy(
                        workExperience = event.workExperience,
                        workExperienceError = error?.message
                    )
                }
            }
        }
    }

    fun bankInfoEvents(event: BankInfoEvents) {
        when (event) {
            is BankInfoEvents.BankNameChanged -> {
                val error = Validator.validateBankName(event.bankName)
                _bankInfoState.update {
                    it.copy(
                        bankName = event.bankName,
                        bankNameError = error?.message
                    )
                }
            }

            is BankInfoEvents.BankBranchNameChanged -> {
                val error = Validator.validateBankBranchName(event.bankBranchName)
                _bankInfoState.update {
                    it.copy(
                        bankBranchName = event.bankBranchName,
                        bankBranchNameError = error?.message
                    )
                }
            }

            is BankInfoEvents.AccountNumberChanged -> {
                val error = Validator.validateAccountNumber(event.accountNumber)
                _bankInfoState.update {
                    it.copy(
                        accountNumber = event.accountNumber,
                        accountNumberError = error?.message
                    )
                }
            }

            is BankInfoEvents.IfscCodeChanged -> {
                val error = Validator.validateIfscCode(event.ifscCode)
                _bankInfoState.update {
                    it.copy(
                        ifscCode = event.ifscCode,
                        ifscCodeError = error?.message
                    )
                }
            }

            is BankInfoEvents.ImageChanged -> {
                val error = Validator.validateImageUri(event.imageUri)
                _bankInfoState.update {
                    it.copy(
                        imageUri = event.imageUri,
                        imageUriError = error?.message
                    )
                }
            }

            is BankInfoEvents.ImageBitmapChanged -> {
                _bankInfoState.update {
                    it.copy(
                        imageBitmap = event.imageBitmap
                    )
                }
            }
        }
    }

    fun addEmployeeEvents(event: AddEmployeeEvents) {
        when (event) {
            is AddEmployeeEvents.AddEmployee -> {
                addProduct(event.addEmployeeRequest)
            }

            AddEmployeeEvents.ClearFailure -> {
                _state.update { it.copy(failure = null) }
            }
        }
    }

    private fun addProduct(fullEmployeeData: FullEmployeeData) {
        _state.update { it.copy(loading = true, isEmployeeAdded = null) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEmployee(fullEmployeeData)
                .onRight {
                    delay(500) // Just to show loading
                    _state.update {
                        it.copy(
                            isEmployeeAdded = "Employee Added Successfully", //temporarily showing success message
                            loading = false
                        )
                    }
                }.onLeft { failure ->
                    _state.update { it.copy(failure = failure, loading = false) }
                }
        }
    }
}
