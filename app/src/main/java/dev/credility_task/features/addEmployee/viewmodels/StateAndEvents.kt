package dev.credility_task.features.addEmployee.viewmodels

import android.graphics.Bitmap
import android.net.Uri
import dev.models.ErrorResponse
import dev.models.FullEmployeeData


data class AddEmployeeStates(
    val loading: Boolean = false,
    val failure: ErrorResponse? = null,
    val isEmployeeAdded: String? = null
)

data class PersonalInfoStates(
    val firstName: String = "",
    val firstNameError: String? = null,

    val lastName: String = "",
    val lastNameError: String? = null,

    val phoneNumber: String = "",
    val phoneNumberError: String? = null,

    val gender: String = "",
    val genderError: String? = null,

    val dob: String = "",
    val dobError: String? = null,
)

data class EmployeeInfoStates(
    val employeeNumber: String = "",
    val employeeNumberError: String? = null,

    val employeeName: String = "",
    val employeeNameError: String? = null,

    val designation: String = "",
    val designationError: String? = null,

    val accountType: String = "",
    val accountTypeError: String? = null,

    val workExperience: String = "",
    val workExperienceError: String? = null,
)

data class BankInfoStates(
    val bankName: String = "",
    val bankNameError: String? = null,
    val bankBranchName: String = "",
    val bankBranchNameError: String? = null,
    val accountNumber: String = "",
    val accountNumberError: String? = null,
    val ifscCode: String = "",
    val ifscCodeError: String? = null,
    val imageUri: Uri? = null,
    val imageUriError: String? = null,
    val imageBitmap: Bitmap? = null,
)

sealed class PersonalInfoEvents {
    data class FirstNameChanged(val firstName: String) : PersonalInfoEvents()
    data class LastNameChanged(val lastName: String) : PersonalInfoEvents()
    data class PhoneNumberChanged(val phoneNumber: String) : PersonalInfoEvents()
    data class GenderChanged(val gender: String) : PersonalInfoEvents()
    data class DobChanged(val dob: String) : PersonalInfoEvents()
}

sealed class EmployeeInfoEvents {
    data class EmployeeNumberChanged(val employeeNumber: String) : EmployeeInfoEvents()
    data class EmployeeNameChanged(val employeeName: String) : EmployeeInfoEvents()
    data class DesignationChanged(val designation: String) : EmployeeInfoEvents()
    data class AccountTypeChanged(val accountType: String) : EmployeeInfoEvents()
    data class WorkExperienceChanged(val workExperience: String) : EmployeeInfoEvents()
}

sealed class BankInfoEvents {
    data class BankNameChanged(val bankName: String) : BankInfoEvents()
    data class BankBranchNameChanged(val bankBranchName: String) : BankInfoEvents()
    data class AccountNumberChanged(val accountNumber: String) : BankInfoEvents()
    data class IfscCodeChanged(val ifscCode: String) : BankInfoEvents()
    data class ImageChanged(val imageUri: Uri) : BankInfoEvents()
    data class ImageBitmapChanged(val imageBitmap: Bitmap) : BankInfoEvents()
}

sealed class AddEmployeeEvents {
    data class AddEmployee(val addEmployeeRequest: FullEmployeeData) : AddEmployeeEvents()
    data object ClearFailure : AddEmployeeEvents()
}
