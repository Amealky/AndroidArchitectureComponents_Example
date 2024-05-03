package io.bm.AndroidArchitectureComponents_Example.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.bm.AndroidArchitectureComponents_Example.api.CoinResultDeserializer
import io.bm.AndroidArchitectureComponents_Example.api.CoinTypeAdapter
import io.bm.AndroidArchitectureComponents_Example.model.Coin
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