package com.ramanifamily.data.repository

import android.content.Context
import com.ramanifamily.data.datastore.loginResponseDataStore
import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.data.datastore.mapper.toProto
import com.ramanifamily.datastore.LoginResponseProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class UserDataStoreRepository (private val context: Context) {

    suspend fun saveLoginResponse(response: LoginResponse) {
        context.loginResponseDataStore.updateData {
            response.toProto()
        }
    }

    //READ login response (Dashboard uses)
    fun getLoginResponse(): Flow<LoginResponseProto> =
        context.loginResponseDataStore.data.catch { emit(LoginResponseProto.getDefaultInstance()) }

    suspend fun clear() {
        context.loginResponseDataStore.updateData {
            LoginResponseProto.getDefaultInstance()
        }
    }
}