package io.bm.aac_example.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.bm.aac_example.api.CoinResultDeserializer
import io.bm.aac_example.api.CoinTypeAdapter
import io.bm.aac_example.model.Coin
import io.bm.aac_example.ui.MainActivity
import java.lang.reflect.Modifier
import javax.inject.Singleton

@Module
abstract class MainActivityModule {

    /*Ici, on définit le module lié à notre MainActivity.
    L'annotation @ContributesAndroidInjector génère un AndroidInjector.
    Ce type d'objet sert à l'injection dans des types Android (Fragments, Activités, Services…).*/
    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity

}