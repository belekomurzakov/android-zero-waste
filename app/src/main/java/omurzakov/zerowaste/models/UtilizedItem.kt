package omurzakov.zerowaste.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UtilizedItem")
class UtilizedItem(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "number") var number: Int,
    @ColumnInfo(name = "createdDate") var createdDate: Long,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    constructor(id: Long?, name: String, image: String, number: Int, createdDate: Long) : this(
        name,
        image,
        number,
        createdDate
    ) {
        this.id = id
    }
}
