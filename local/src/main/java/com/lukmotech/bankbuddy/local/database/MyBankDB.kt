package com.lukmotech.bankbuddy.local.database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lukmotech.bankbuddy.local.model.TransactionLocal
import com.lukmotech.bankbuddy.local.model.UserInfoLocal

@Database(
    entities = [UserInfoLocal::class, TransactionLocal::class],
    version = 1,
    exportSchema = false
)
abstract class MyBankDB : RoomDatabase() {
    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "my_bank.db"

        @Volatile
        private var INSTANCE: MyBankDB? = null

        fun getInstance(@NonNull context: Context): MyBankDB {
            if(INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            MyBankDB::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
        abstract fun getTransactionDao(): TransactionDao

        abstract fun getUserInfoDao(): UserInfoDao
}