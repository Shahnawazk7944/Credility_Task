package dev.data.local.extensions

import dev.data.local.entity.EmployeeEntity
import dev.models.FullEmployeeData


fun EmployeeEntity.toFullEmployeeData(): FullEmployeeData {
    return FullEmployeeData(
        id = this.id,
        personalInfo = this.dataJson.personalInfo,
        employeeInfo = this.dataJson.employeeInfo,
        bankInfo = this.dataJson.bankInfo,
        imageUri = this.dataJson.imageUri
    )
}

fun List<EmployeeEntity>.toFullEmployeeDataList(): List<FullEmployeeData> {
    return this.map {
        FullEmployeeData(
            id = it.id,
            personalInfo = it.dataJson.personalInfo,
            employeeInfo = it.dataJson.employeeInfo,
            bankInfo = it.dataJson.bankInfo,
            imageUri = it.dataJson.imageUri
        )
    }
}