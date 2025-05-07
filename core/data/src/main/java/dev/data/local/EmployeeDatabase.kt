package dev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.data.local.dao.EmployeeDao
import dev.data.local.entity.EmployeeEntity
import dev.data.local.extensions.EmployeeDataTypeConverters

@Database(version = 1, entities = [EmployeeEntity::class])
@TypeConverters(EmployeeDataTypeConverters::class)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}