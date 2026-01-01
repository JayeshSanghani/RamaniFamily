package com.ramanifamily.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.ramanifamily.datastore.LoginResponseProto

private const val DATASTORE_NAME = "login_response.pb"

val Context.loginResponseDataStore: DataStore<LoginResponseProto> by dataStore(
    fileName = DATASTORE_NAME,
    serializer = LoginResponseSerializer
)