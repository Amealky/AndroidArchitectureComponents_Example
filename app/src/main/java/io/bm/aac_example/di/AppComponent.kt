package io.bm.aac_example.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.bm.aac_example.AACExampleApp
import javax.inject.Singleton


/*Ce composant contient 3 modules fils :

AndroidInjectionModule (contenu dans dagger-android)
AppModule
MainActivityModule

Nous définissons dans l'interface Builder le binding entre AppComponent
et un objet Application. Compiler l'application.
La compilation générera une classe DaggerAppComponent que nous utiliserons par la suite.*/
@Singleton
@Component(modules = [
    (AndroidInjectionModule::class),
    (AppModule::class),
    (MainActivityModule::class)
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(aacExampleApp: AACExampleApp)

}