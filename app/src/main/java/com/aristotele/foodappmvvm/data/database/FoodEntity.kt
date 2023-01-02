package com.aristotele.foodappmvvm.data.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aristotele.foodappmvvm.utils.FOOD_DB_TABLE

@Entity(tableName = FOOD_DB_TABLE)
data class FoodEntity(
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var img: String = ""
)