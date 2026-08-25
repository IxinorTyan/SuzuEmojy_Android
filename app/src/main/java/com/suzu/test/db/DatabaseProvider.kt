package com.suzu.test.db

import android.content.Context

object DatabaseProvider {

    @Volatile
    private var instance: SuzuDatabase? = null

    fun getDatabase(context: Context): SuzuDatabase {
        return instance ?: synchronized(this) {
            instance ?: SuzuDatabase.getInstance(context.applicationContext).also {
                instance = it
            }
        }
    }
}
