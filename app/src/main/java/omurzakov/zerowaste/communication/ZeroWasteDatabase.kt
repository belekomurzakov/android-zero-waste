package omurzakov.zerowaste.communication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import omurzakov.zerowaste.models.UtilizedItem

@Database(entities = [UtilizedItem::class], version = 1, exportSchema = true)
abstract class ZeroWasteDatabase : RoomDatabase() {
    abstract fun utilizedItemDao(): UtilizedItemDao

    companion object {
        @Volatile
        private var INSTANCE: ZeroWasteDatabase? = null

        fun getDatabase(context: Context): ZeroWasteDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, ZeroWasteDatabase::class.java, "database"
        ).allowMainThreadQueries().addCallback(object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }).build()
    }
}