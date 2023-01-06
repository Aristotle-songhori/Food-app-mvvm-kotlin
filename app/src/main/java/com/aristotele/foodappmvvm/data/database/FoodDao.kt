package com.aristotele.foodappmvvm.data.database

import androidx.room.*
import com.aristotele.foodappmvvm.utils.FOOD_DB_TABLE
import kotlinx.coroutines.flow.Flow

/**
 * اینجا با استفاده از کوروتین و فلو و لایو دیتا
 * میایم و عملیات روی دیتابیس رو تعریف میکنیم
 */
@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFood(entity: FoodEntity)

    @Delete
    suspend fun deleteFood(entity: FoodEntity)

    @Query("SELECT * FROM $FOOD_DB_TABLE")
    fun getAllFoods(): Flow<MutableList<FoodEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM $FOOD_DB_TABLE WHERE id = :id)")
    fun existsFood(id: Int): Flow<Boolean>
}

