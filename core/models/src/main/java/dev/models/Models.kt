package dev.models

data class FullEmployeeData(
    val id: Int? = null,
    val personalInfo: PersonalInfo,
    val employeeInfo: EmployeeInfo,
    val bankInfo: BankInfo,
    val imageUri: String? = null
)

data class PersonalInfo(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val gender: String,
    val dob: String
)

data class EmployeeInfo(
    val employeeNo: String,
    val employeeName: String,
    val designation: String,
    val accountType: String,
    val experience: String
)

data class BankInfo(
    val bankName: String,
    val branchName: String,
    val accountNo: String,
    val ifscCode: String
)

data class ErrorResponse(
    val error: String? = null,
    val message: String? = null
)