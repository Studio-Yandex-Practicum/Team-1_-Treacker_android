package core.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import core.db.entity.CategoryEntity
import core.db.entity.OperationEntity

data class CategoryAndOperationRelation(
    @Embedded
    val category: CategoryEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val operations: List<OperationEntity>
)