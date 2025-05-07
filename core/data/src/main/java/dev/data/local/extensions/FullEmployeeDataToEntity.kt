package dev.data.local.extensions

import dev.data.local.entity.EmployeeEntity
import dev.models.FullEmployeeData

fun FullEmployeeData.toEmployeeEntity(id: Int = 0): EmployeeEntity {
    return EmployeeEntity(
        id = id,
        dataJson = this,
    )
}