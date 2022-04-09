package com.ke.zhihu.module.di

import android.content.Context
import androidx.room.Room
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.ke.zhihu.api.ZhihuApi
import com.ke.zhihu.module.data.KeyValueStore
import com.ke.zhihu.module.data.MMKVKeyValueStore
import com.ke.zhihu.module.data.UserDataStore
import com.ke.zhihu.module.db.AccountDao
import com.ke.zhihu.module.db.ZhihuDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleModule {

    @Provides
    @Singleton
    fun provideCookieJar(@ApplicationContext context: Context): CookieJar {
        return PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
    }

    @Provides
    @Singleton
    fun provideKeyValueStore(mmkvKeyValueStore: MMKVKeyValueStore): KeyValueStore {
        return mmkvKeyValueStore
    }

    @Provides
    @Singleton
    fun provideZhihuDatabase(@ApplicationContext context: Context): ZhihuDatabase {
        return Room.databaseBuilder(context, ZhihuDatabase::class.java, "zhihu.db")
            .build()
    }

    @Provides
    fun provideAccountDao(zhihuDatabase: ZhihuDatabase): AccountDao {
        return zhihuDatabase.accountDao()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        cookieJar: CookieJar,
        userDataStore: UserDataStore
//        accountDao: AccountDao

    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor { chain ->


                val original = chain.request()


                val builder = original.newBuilder()
                    .header("x-app-version", "8.7.0")
                    .header("x-api-version", "3.1.8")
                    .header(
                        "x-app-za",
                        "OS=Android&Release=6.0.1&Model=ONEPLUS+A3000&VersionName=8.7.0&VersionCode=8730&Product=com.zhihu.android&Width=1080&Height=1920&Installer=OPPO%E8%BD%AF%E4%BB%B6%E5%95%86%E5%BA%97&DeviceType=AndroidPhone&Brand=OnePlus"
                    )
                    .header("Authorization", userDataStore.authorization ?: "")

//                if (original.url()
//                        .queryParameter("action") == "ignore_token"
//                ) {
//                    builder.header("Authorization", runBlocking {
//                        accountDao.getCurrent()!!.token
//                    })
//                }

                val request = builder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideZhihuApi(okHttpClient: OkHttpClient): ZhihuApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://api.zhihu.com/")
            .build()
            .create(ZhihuApi::class.java)
    }


}