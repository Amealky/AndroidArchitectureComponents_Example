package io.bm.aac_example.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.bm.aac_example.api.CoinResultDeserializer
import io.bm.aac_example.api.CoinTypeAdapter
import io.bm.aac_example.model.Coin
import java.lang.reflect.Modifier
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideGson() : Gson {
        return GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .registerTypeAdapter(Coin::class.java, CoinTypeAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }


    @Singleton @Provides
    fun provideCoinResultDeserializer(gson: Gson): CoinResultDeserializer {
        return CoinResultDeserializer(gson)
    }


}