package com.aristotele.foodappmvvm.data.repository

import com.aristotele.foodappmvvm.data.database.FoodDao
import com.aristotele.foodappmvvm.data.database.FoodEntity
import com.aristotele.foodappmvvm.data.model.ResponseFoodsList
import com.aristotele.foodappmvvm.data.server.ApiServices
import com.aristotele.foodappmvvm.utils.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FoodDetailRepository @Inject constructor(private val api: ApiServices, private val dao: FoodDao) {

    suspend fun foodDetail(id: Int): Flow<MyResponse<ResponseFoodsList>> {
        return flow {
            emit(MyResponse.loading())
            when (api.foodDetail(id).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(api.foodDetail(id).body()))
                }
            }
        }.catch { emit(MyResponse.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }

    suspend fun saveFood(entity: FoodEntity) = dao.saveFood(entity)
    suspend fun deleteFood(entity: FoodEntity) = dao.deleteFood(entity)
    fun existsFood(id: Int) = dao.existsFood(id)
}