package dev.data.local.extensions

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import dev.models.FullEmployeeData

@ProvidedTypeConverter
class EmployeeDataTypeConverters() {
    private val gson = Gson()

    @TypeConverter
    fun fromFullEmployeeData(data: FullEmployeeData): String = gson.toJson(data)

    @TypeConverter
    fun toFullEmployeeData(json: String): FullEmployeeData =
        gson.fromJson(json, FullEmployeeData::class.java)
}