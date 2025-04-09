package core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import core.db.entity.OperationEntity

@Dao
interface OperationDao {

    companion object {
        const val TABLE_NAME = "Operation"
    }

    @Insert
    suspend fun saveOperation(operationEntity: OperationEntity)
}