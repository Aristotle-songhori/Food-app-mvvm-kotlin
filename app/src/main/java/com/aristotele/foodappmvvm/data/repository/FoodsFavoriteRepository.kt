package com.aristotele.foodappmvvm.data.repository


import com.aristotele.foodappmvvm.data.database.FoodDao
import javax.inject.Inject

class FoodsFavoriteRepository @Inject constructor(private val dao: FoodDao) {
    fun foodsList() = dao.getAllFoods()
}