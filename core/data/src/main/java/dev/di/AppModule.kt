package dev.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.data.local.EmployeeDatabase
import dev.data.local.EmployeeLocalDataSource
import dev.data.local.dao.EmployeeDao
import dev.data.local.extensions.EmployeeDataTypeConverters
import dev.data.repository.EmployeeRepository
import dev.data.repository.EmployeeRepositoryImpl
import dev.data.util.Constants.EMPLOYEE_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEmployeeDatabase(@ApplicationContext context: Context): EmployeeDatabase {
        return Room.databaseBuilder(
            context,
            EmployeeDatabase::class.java,
            EMPLOYEE_DATABASE_NAME
        ).addTypeConverter(EmployeeDataTypeConverters())
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Singleton
    @Provides
    fun provideEmployeeDao(employeeDatabase: EmployeeDatabase): EmployeeDao {
        return employeeDatabase.employeeDao()
    }
}

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun provideEmployeeLocalDataSource(
        dao: EmployeeDao,
    ): EmployeeLocalDataSource {
        return EmployeeLocalDataSource(dao)
    }

    @Provides
    fun provideEmployeeRepository(
        localDataSource: EmployeeLocalDataSource
    ): EmployeeRepository {
        return EmployeeRepositoryImpl(localDataSource)
    }
}
