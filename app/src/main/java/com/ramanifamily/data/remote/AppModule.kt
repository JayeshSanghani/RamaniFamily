package com.ramanifamily.data.remote

import android.content.Context
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.common.network.NetworkCheckerImpl
import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.repository.LoginRepositoryImpl
import com.ramanifamily.data.repository.RegisterRepositoryImpl
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.repository.LoginRepository
import com.ramanifamily.domain.repository.RegisterRepository
import com.ramanifamily.domain.usecase.LoginUserUseCase
import com.ramanifamily.domain.usecase.RegisterUserUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import okhttp3.Response

object AppModule {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://ramaniparivar.com/api/")
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)

//        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(logging)
//        }


        builder.addInterceptor(DefaultHeaderInterceptor())

        return builder.build()
    }


    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val registerRepository: RegisterRepository by lazy {
        RegisterRepositoryImpl(apiService)
    }

    val registerUserUseCase: RegisterUserUseCase by lazy {
        RegisterUserUseCase(registerRepository)
    }

    private val loginRepository: LoginRepository by lazy {
        LoginRepositoryImpl(apiService)
    }

    val loginUserUseCase: LoginUserUseCase by lazy {
        LoginUserUseCase(loginRepository)
    }
    //proto
    val userDataStoreRepository: UserDataStoreRepository by lazy {
        UserDataStoreRepository(appContext)
    }

    val networkChecker: NetworkChecker by lazy {
        NetworkCheckerImpl(appContext)
    }


}


class DefaultHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val request = originalRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}
