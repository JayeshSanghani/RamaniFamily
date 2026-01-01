package com.ramanifamily.data.datastore

import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.ramanifamily.datastore.LoginResponseProto
import java.io.InputStream
import java.io.OutputStream

object LoginResponseSerializer : Serializer<LoginResponseProto> {

    override val defaultValue: LoginResponseProto = LoginResponseProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LoginResponseProto =
        try {
            LoginResponseProto.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            defaultValue
        }

    override suspend fun writeTo(
        t: LoginResponseProto,
        output: OutputStream
    ) = t.writeTo(output)
}