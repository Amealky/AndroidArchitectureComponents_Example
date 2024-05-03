package io.bm.AndroidArchitectureComponents_Example.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.bm.AndroidArchitectureComponents_Example.ui.MainActivity

@Module
abstract class MainActivityModule {

    /*Ici, on définit le module lié à notre MainActivity.
    L'annotation @ContributesAndroidInjector génère un AndroidInjector.
    Ce type d'objet sert à l'injection dans des types Android (Fragments, Activités, Services…).*/
    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity

}