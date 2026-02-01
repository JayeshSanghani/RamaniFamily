package com.ramanifamily.data.remote

//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
import android.content.Context
import com.ramanifamily.common.network.NetworkChecker
import com.ramanifamily.common.network.NetworkCheckerImpl
import com.ramanifamily.data.api.ApiService
import com.ramanifamily.data.repository.AddMemberRepositoryImpl
import com.ramanifamily.data.repository.LoginRepositoryImpl
import com.ramanifamily.data.repository.MemberListRepositoryImpl
import com.ramanifamily.data.repository.ProfileBusinessRepositoryImpl
import com.ramanifamily.data.repository.ProfileMaritalRepositoryImpl
import com.ramanifamily.data.repository.ProfilePersonalRepositoryImpl
import com.ramanifamily.data.repository.RegisterRepositoryImpl
import com.ramanifamily.data.repository.UserDataStoreRepository
import com.ramanifamily.domain.repository.AddMemberRepository
import com.ramanifamily.domain.repository.LoginRepository
import com.ramanifamily.domain.repository.MemberListRepository
import com.ramanifamily.domain.repository.ProfileBusinessRepository
import com.ramanifamily.domain.repository.ProfileMaritalRepository
import com.ramanifamily.domain.repository.ProfilePersonalRepository
import com.ramanifamily.domain.repository.RegisterRepository
import com.ramanifamily.domain.usecase.AddMemberUseCase
import com.ramanifamily.domain.usecase.GetDistrictsUseCase
import com.ramanifamily.domain.usecase.GetSubDistrictsUseCase
import com.ramanifamily.domain.usecase.LoginUserUseCase
import com.ramanifamily.domain.usecase.MemberListUseCase
import com.ramanifamily.domain.usecase.ProfileBusinessUseCase
import com.ramanifamily.domain.usecase.ProfileMaritalUseCase
import com.ramanifamily.domain.usecase.ProfilePersonalUseCase
import com.ramanifamily.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideContext(
//        @ApplicationContext context: Context
//    ): Context = context

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

                .addInterceptor(
                    DefaultHeaderInterceptor(userDataStoreRepository)
                )
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

    private val addMemberRepository: AddMemberRepository by lazy {
        AddMemberRepositoryImpl(apiService)
    }

    val addMemberUseCase: AddMemberUseCase by lazy {
        AddMemberUseCase(addMemberRepository)
    }

    val memberListRepository: MemberListRepository by lazy {
        MemberListRepositoryImpl(apiService)
    }

    val memberListUseCase: MemberListUseCase by lazy {
        MemberListUseCase(memberListRepository)
    }

    val profilePersonalRepository: ProfilePersonalRepository by lazy {
        ProfilePersonalRepositoryImpl(apiService)
    }

    val profilePersonalUseCase: ProfilePersonalUseCase by lazy {
        ProfilePersonalUseCase(profilePersonalRepository)
    }

    val profileBusinessRepository: ProfileBusinessRepository by lazy {
        ProfileBusinessRepositoryImpl(apiService)
    }

    val profileBusinessUseCase: ProfileBusinessUseCase by lazy {
        ProfileBusinessUseCase(profileBusinessRepository)
    }

    val profileMaritalRepository: ProfileMaritalRepository by lazy {
        ProfileMaritalRepositoryImpl(apiService)
    }

    val profileMaritalUseCase: ProfileMaritalUseCase by lazy {
        ProfileMaritalUseCase(profileMaritalRepository)
    }

    val getDistrictsUseCase by lazy {
        GetDistrictsUseCase(registerRepository)
    }

    val getSubDistrictsUseCase by lazy {
        GetSubDistrictsUseCase(registerRepository)
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


class DefaultHeaderInterceptor( private val userDataStoreRepository: UserDataStoreRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking {
            userDataStoreRepository
                .getLoginResponse()
                .first()
                .token
        }
        val request = originalRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}
