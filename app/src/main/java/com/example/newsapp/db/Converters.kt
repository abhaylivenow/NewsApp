package com.example.newsapp.db

import androidx.room.TypeConverter
import com.example.newsapp.model.Source

class Converters {
    /*
    Since room db cannot handle other than primitive data types,
    that's why we have converter to handle non-primitive data types
    'Source' is a non-primitive data type that comes in response,
    so we have to handle accordingly
     */
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}