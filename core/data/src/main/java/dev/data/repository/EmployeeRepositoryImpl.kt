package dev.data.repository

import arrow.core.Either
import dev.data.local.EmployeeLocalDataSource
import dev.models.ErrorResponse
import dev.models.FullEmployeeData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val localDataSource: EmployeeLocalDataSource
) : EmployeeRepository {

    override suspend fun addEmployee(fullEmployeeData: FullEmployeeData): Either<ErrorResponse, Unit> {
        return try {
            localDataSource.insertEmployee(fullEmployeeData)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(ErrorResponse("Failed to add employee", e.localizedMessage))
        }
    }

    override suspend fun updateEmployee(
        id: Int,
        fullEmployeeData: FullEmployeeData
    ): Either<ErrorResponse, Unit> {
        return try {
            localDataSource.updateEmployee(id, fullEmployeeData)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(ErrorResponse("Failed to update employee", e.localizedMessage))
        }
    }

    override suspend fun getAllEmployees(): Either<ErrorResponse, Flow<List<FullEmployeeData>>> {
        return try {
            Either.Right(localDataSource.getAllEmployee())
        } catch (e: Exception) {
            Either.Left(ErrorResponse("Failed to get all employees", e.localizedMessage))
        }
    }

    override suspend fun getEmployeeById(id: Int): Either<ErrorResponse, FullEmployeeData> {
        return try {
            val employeeData = localDataSource.getEmployeeById(id)
            Either.Right(employeeData)
        } catch (e: Exception) {
            Either.Left(ErrorResponse("Employee not found", e.localizedMessage))
        }
    }

    override suspend fun deleteEmployee(id: Int): Either<ErrorResponse, Unit> {
        return try {
            localDataSource.deleteEmployee(id)
            Either.Right(Unit)
        } catch (e: Exception) {
            Either.Left(ErrorResponse("Failed to delete employee", e.localizedMessage))
        }
    }
}