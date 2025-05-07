package dev.data.local

import dev.data.local.dao.EmployeeDao
import dev.data.local.extensions.toEmployeeEntity
import dev.data.local.extensions.toFullEmployeeData
import dev.data.local.extensions.toFullEmployeeDataList
import dev.models.FullEmployeeData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class EmployeeLocalDataSource @Inject constructor(
    private val dao: EmployeeDao
) {

    suspend fun insertEmployee(user: FullEmployeeData) {
        dao.insert(user.toEmployeeEntity())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllEmployee(): Flow<List<FullEmployeeData>> {
        return dao.getAllEmployees().mapLatest { it.toFullEmployeeDataList() }
    }

    suspend fun getEmployeeById(id: Int): FullEmployeeData {
        return dao.getEmployeeById(id).toFullEmployeeData()
    }

    suspend fun updateEmployee(id: Int, user: FullEmployeeData) {
        dao.update(user.toEmployeeEntity(id))
    }

    suspend fun deleteEmployee(id: Int) {
        val entity = dao.getEmployeeById(id)
        dao.delete(entity)
    }
}
