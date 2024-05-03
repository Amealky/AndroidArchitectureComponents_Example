package io.bm.AndroidArchitectureComponents_Example.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import awaitObjectResult
import com.github.kittinunf.fuel.Fuel
import io.bm.AndroidArchitectureComponents_Example.api.CoinResultDeserializer
import io.bm.AndroidArchitectureComponents_Example.api.CoinRouting
import io.bm.AndroidArchitectureComponents_Example.model.Coin
import io.bm.AndroidArchitectureComponents_Example.utils.Resource
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/*CoinViewModel ici a un double rôle :

Service, puisque c'est ici qu'on réalise l'appel à coinmarket
Data Holder, via la variable d'instance coins
Nous annotons le primary constructor de CoinViewModel avec @Inject. C'est parce qu'il sera
initialisé via injection de dépendances dans MainActivity.

Cette annotation permet aussi d'injecter coinsResultDeserializer. Nous le pouvons grâce à notre configuration Dagger dans
 ppModule (cf l'article précédent) :*/

@Singleton
class CoinViewModel @Inject constructor(
    private val coinResultDeserializer: CoinResultDeserializer
): ViewModel() {
    val coins: MutableLiveData<Resource<Array<Coin>>> = MutableLiveData()

    /*GlobalScope.produce(Dispatchers.Default, 0) {...} crée une coroutine.
    Le type de Dispatchers spécifie son contexte d'exécution. Default correspond à un pool de threads partagé sur la JVM.*/

    //Producer
    private suspend fun getCoins() = GlobalScope.produce(Dispatchers.Default, 0) {
        val emptyData: Array<Coin> = emptyArray()
        send(Resource.loading(emptyData))

        Fuel.request(CoinRouting.GetCoins())
            .awaitObjectResult(coinResultDeserializer)
            .fold(success = { response ->
                send(Resource.success(response.data))
            }, failure = { error ->
                send(Resource.error(error, emptyData))
            })
    }

    //Consumer
    fun fetchCoins() = GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
        getCoins().consumeEach { it ->
            coins.value = it
        }
    }
}