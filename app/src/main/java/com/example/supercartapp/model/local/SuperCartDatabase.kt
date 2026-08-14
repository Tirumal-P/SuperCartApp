package com.example.supercartapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.supercartapp.model.local.dao.CartDao
import com.example.supercartapp.model.local.entity.CartEntity
import com.example.supercartapp.model.local.entity.CartItemEntity

@Database(
    entities = [
        CartEntity::class,
        CartItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SuperCartDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object{

        @Volatile
        private var INSTANCE: SuperCartDatabase? = null

        fun getDatabase(context: Context): SuperCartDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context= context.applicationContext,
                    SuperCartDatabase::class.java,
                    "SuperCartDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}