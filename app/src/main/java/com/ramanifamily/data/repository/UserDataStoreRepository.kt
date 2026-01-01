package com.ramanifamily.data.repository

import android.content.Context
import com.ramanifamily.data.datastore.loginResponseDataStore
import com.ramanifamily.data.entity.LoginResponse
import com.ramanifamily.data.datastore.mapper.toProto
import com.ramanifamily.datastore.LoginResponseProto
import kotlinx.coroutines.flow.Flow

class UserDataStoreRepository(
    private val context: Context
) {

    suspend fun saveLoginResponse(response: LoginResponse) {
        context.loginResponseDataStore.updateData {
            response.toProto()
        }
    }

    val loginResponseFlow: Flow<LoginResponseProto> =
        context.loginResponseDataStore.data

    suspend fun clear() {
        context.loginResponseDataStore.updateData {
            LoginResponseProto.getDefaultInstance()
        }
    }
}