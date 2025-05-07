package dev.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.models.FullEmployeeData

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dataJson: FullEmployeeData
)