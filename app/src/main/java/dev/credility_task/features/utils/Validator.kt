package dev.credility_task.features.utils

import android.net.Uri
import dev.credility_task.features.addEmployee.viewmodels.BankInfoStates
import dev.credility_task.features.addEmployee.viewmodels.EmployeeInfoStates
import dev.credility_task.features.addEmployee.viewmodels.PersonalInfoStates
import dev.models.BankInfo
import dev.models.EmployeeInfo
import dev.models.PersonalInfo


data class ValidationError(val message: String)

object Validator {

    fun validateFirstName(firstName: String): ValidationError? {
        if (firstName.isBlank()) return ValidationError("First name cannot be empty")
        if (firstName.length < 3) return ValidationError("First name must be at least 3 characters")
        if (firstName.any { it.isDigit() }) return ValidationError("First name cannot contain digits")
        return null
    }

    fun validateLastName(lastName: String): ValidationError? {
        if (lastName.isBlank()) return ValidationError("Last name cannot be empty")
        if (lastName.length < 3) return ValidationError("Last name must be at least 3 characters")
        if (lastName.any { it.isDigit() }) return ValidationError("Last name cannot contain digits")
        return null
    }

    fun validatePhoneNumber(phone: String): ValidationError? {
        if (phone.isBlank()) return ValidationError("Phone number cannot be empty")
        if (!phone.matches(Regex("^[6-9]\\d{9}$"))) return ValidationError("Enter a valid 10-digit phone number")
        return null
    }

    fun validateGender(gender: String): ValidationError? {
        if (gender.isBlank()) return ValidationError("Please select a gender")
        if (gender !in listOf(
                "Male",
                "Female",
                "Others"
            )
        ) return ValidationError("Invalid gender selected")
        return null
    }

    fun validateDob(dob: String): ValidationError? {
        if (dob.isBlank()) return ValidationError("Date of birth cannot be empty")
        return null
    }

    fun validateEmployeeNumber(empNo: String): ValidationError? {
        if (empNo.isBlank()) return ValidationError("Employee number cannot be empty")
        if (!empNo.matches(Regex("^[A-Za-z0-9]{3,}$"))) return ValidationError("Invalid employee number")
        return null
    }

    fun validateEmployeeName(empName: String): ValidationError? {
        if (empName.isBlank()) return ValidationError("Employee name cannot be empty")
        if (empName.any { it.isDigit() }) return ValidationError("Name should not contain numbers")
        return null
    }

    fun validateDesignation(designation: String): ValidationError? {
        if (designation.isBlank()) return ValidationError("Designation cannot be empty")
        return null
    }

    fun validateAccountType(accountType: String): ValidationError? {
        if (accountType.isBlank()) return ValidationError("Please select an account type")
        return null
    }

    fun validateWorkExperience(workExp: String): ValidationError? {
        if (workExp.isBlank()) return ValidationError("Please select work experience")
        return null
    }

    fun validateBankName(bankName: String): ValidationError? {
        if (bankName.isBlank()) return ValidationError("Bank name cannot be empty")
        return null
    }

    fun validateBankBranchName(branch: String): ValidationError? {
        if (branch.isBlank()) return ValidationError("Branch name cannot be empty")
        return null
    }

    fun validateAccountNumber(accountNumber: String): ValidationError? {
        if (accountNumber.isBlank()) return ValidationError("Account number cannot be empty")
        if (!accountNumber.matches(Regex("^\\d{9,18}$"))) return ValidationError("Enter a valid account number")
        return null
    }

    fun validateIfscCode(ifsc: String): ValidationError? {
        if (ifsc.isBlank()) return ValidationError("IFSC code cannot be empty")
        if (!ifsc.matches(Regex("^[A-Z]{4}0[A-Z0-9]{6}$"))) return ValidationError("Enter a valid IFSC code")
        return null
    }

    fun validateImageUri(imageUri: Uri?): ValidationError? {
        if (imageUri == null) return ValidationError("Please select document")
        return null
    }
}

fun PersonalInfoStates.isFormValid(): Boolean {
    return firstNameError == null &&
            lastNameError == null &&
            phoneNumberError == null &&
            genderError == null &&
            dobError == null &&
            firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            phoneNumber.isNotBlank() &&
            gender.isNotBlank() &&
            dob.isNotBlank()
}

fun EmployeeInfoStates.isFormValid(): Boolean {
    return employeeNumberError == null &&
            employeeNameError == null &&
            designationError == null &&
            accountTypeError == null &&
            workExperienceError == null &&
            employeeNumber.isNotBlank() &&
            employeeName.isNotBlank() &&
            designation.isNotBlank() &&
            accountType.isNotBlank() &&
            workExperience.isNotBlank()
}

fun BankInfoStates.isFormValid(): Boolean {
    return bankNameError == null &&
            bankBranchNameError == null &&
            accountNumberError == null &&
            ifscCodeError == null &&
            imageUriError == null &&
            bankName.isNotBlank() &&
            bankBranchName.isNotBlank() &&
            accountNumber.isNotBlank() &&
            ifscCode.isNotBlank() &&
            imageUri != null
}

fun PersonalInfoStates.toPersonalInfo(): PersonalInfo {
    return PersonalInfo(
        firstName = firstName,
        lastName = lastName,
        phone = phoneNumber,
        gender = gender,
        dob = dob
    )
}

fun EmployeeInfoStates.toEmployeeInfo(): EmployeeInfo {
    return EmployeeInfo(
        employeeNo = employeeNumber,
        employeeName = employeeName,
        designation = designation,
        accountType = accountType,
        experience = workExperience
    )
}

fun BankInfoStates.toBankInfo(): BankInfo {
    return BankInfo(
        bankName = bankName,
        branchName = bankBranchName,
        accountNo = accountNumber,
        ifscCode = ifscCode
    )
}


