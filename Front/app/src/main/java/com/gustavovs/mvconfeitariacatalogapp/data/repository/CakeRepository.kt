package com.gustavovs.mvconfeitariacatalogapp.data.repository

import com.gustavovs.mvconfeitariacatalogapp.data.model.Cake
import com.gustavovs.mvconfeitariacatalogapp.data.remote.CakeApi
import com.gustavovs.mvconfeitariacatalogapp.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CakeRepository(private val api: CakeApi = RetrofitClient.cakeApi) {

    suspend fun getCakes(): Result<List<Cake>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getCakes()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                val errorBody = response.errorBody()?.string() ?: "Erro desconhecido"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchCakes(title: String): Result<List<Cake>> = withContext(Dispatchers.IO) {
        try {
            val response = api.findCakesByTitle(title)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                val errorBody = response.errorBody()?.string() ?: "Erro desconhecido"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createCake(cake: Cake): Result<Cake> = withContext(Dispatchers.IO) {
        try {
            val response = api.createCake(cake)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Erro desconhecido ao salvar bolo"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateCake(id: Long, cake: Cake): Result<Cake> = withContext(Dispatchers.IO) {
        try {
            val response = api.updateCake(id, cake)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Erro desconhecido ao atualizar bolo"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteCake(id: Long): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = api.deleteCake(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Erro desconhecido ao deletar"
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
