package com.example.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.Update

@Entity
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM MenuItemRoom")
    fun getAll(): LiveData<List<MenuItemRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menuItem: MenuItemRoom)

    @Transaction
    fun insertOrUpdateAll(menuItems: List<MenuItemRoom>) {
        for (menuItem in menuItems) {
            insertOrUpdate(menuItem)
        }
    }

    @Transaction
    fun insertOrUpdate(menuItem: MenuItemRoom) {
        val existingItem = getById(menuItem.id)
        if (existingItem == null) {
            insert(menuItem)
        } else {
            update(menuItem)
        }
    }

    @Query("SELECT * FROM MenuItemRoom WHERE id = :id")
    fun getById(id: Int): MenuItemRoom?

    @Update
    fun update(menuItem: MenuItemRoom)
}


@Database(entities = [MenuItemRoom::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}