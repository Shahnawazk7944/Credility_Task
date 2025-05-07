package dev.data.repository

import arrow.core.Either
import dev.models.ErrorResponse
import dev.models.FullEmployeeData
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {

    suspend fun addEmployee(
        fullEmployeeData: FullEmployeeData
    ): Either<ErrorResponse, Unit>

    suspend fun updateEmployee(
        id: Int,
        fullEmployeeData: FullEmployeeData
    ): Either<ErrorResponse, Unit>

    suspend fun getAllEmployees(): Either<ErrorResponse, Flow<List<FullEmployeeData>>>

    suspend fun getEmployeeById(id: Int): Either<ErrorResponse, FullEmployeeData>

    suspend fun deleteEmployee(id: Int): Either<ErrorResponse, Unit>
}