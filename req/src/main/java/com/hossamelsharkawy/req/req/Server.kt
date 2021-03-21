package com.hossamelsharkawy.req.req

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Job
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Hossam Elsharkawy
0201099197556
on 6/11/2018.  time :17:16

 */
object Server {

    lateinit var mBaseUrl: String
    lateinit var cache: Cache


    const val serverErrorMsg = "Server ErrorMsg"
    const val invalid_credentialsMsg = "Invalid Credentials"
    const val noInternetConnectionMsg = "No Internet Connection"
    const val socketTimeoutMsg = "Socket Timeout"
    const val connectionErrorMsg = "Connection Error"
    const val protocolExceptionMsg = "Protocol Exception"
    const val connectionAbortMsg = "Connection Abort !!" //
    const val cancellationExceptionMsg = "Cancellation Exception"


    var ApiJob = Job()

    fun setBaseUrl(mBaseUrl: String) = apply {
        Server.mBaseUrl = mBaseUrl
    }

    fun setCache(cache: Cache) = apply {
        Server.cache = cache
    }

    var authenticator: Authenticator? = null
    var authenticationInterceptor: Interceptor? = null

    fun setAuth(
        authenticator: Authenticator? = null,
        authenticationInterceptor: Interceptor? = null
    ) = apply {
        Server.authenticator = authenticator
        Server.authenticationInterceptor = authenticationInterceptor
    }


    private val retrofit by lazy {
        setRetrofit()
    }
    private val retrofitGust by lazy {
        Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(TryCallAdapter(true))
            .client(clientGust)
            .build()
    }
    val client: OkHttpClient?
        get() = provideOkHttpClient()


    val clientGust by lazy {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(50L, TimeUnit.SECONDS)
            .writeTimeout(50L, TimeUnit.SECONDS)
            .readTimeout(50L, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }


    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }


    fun <T> createGust(service: Class<T>): T {
        return retrofitGust.create(service)
    }


    private fun setRetrofit(): Retrofit {

        /*     val mapper = ObjectMapper()
             mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
             mapper.dateFormat = DateUtils.f

             val ptv: PolymorphicTypeValidator = BasicPolymorphicTypeValidator.builder()
                 //.allowIfSubType(BaseModelTest::class.java)
                 .allowIfBaseType(BaseModelTest::class.java)
                 .build()

             mapper.activateDefaultTyping(ptv); // default to using DefaultTyping.OBJECT_AND_NON_CONCRETE*/
        //  mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);


        return Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            // .addConverterFactory(JacksonConverterFactory.create(mapper))
         //   .addCallAdapterFactory(TryCallAdapter(false))
            .client(client)
            .build()
    }

    fun getGson(): Gson {
        return GsonBuilder()
            // .registerTypeAdapter(BaseModel::class.java,  BaseAdaptor())
            .setDateFormat("yyyy-MM-dd HH:mm:ssZ")
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }


    private fun provideOkHttpClient(): OkHttpClient {
        // val cacheSize = 100 * 1024 * 1024 // 100 MiB
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(50L, TimeUnit.SECONDS)
            .writeTimeout(50L, TimeUnit.SECONDS)
            .readTimeout(50L, TimeUnit.SECONDS)
            .addInterceptor(logging)
            /*     .addInterceptor(CurlInterceptor( Loggable {
                     Log.d("Ok2Curl", it)
                 }))*/
            // .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)

        if (authenticationInterceptor != null) {
            client.addInterceptor(authenticationInterceptor!!)
        }

        if (authenticator != null) {
            client.authenticator(authenticator!!)
        }
        return client.build()
    }


}