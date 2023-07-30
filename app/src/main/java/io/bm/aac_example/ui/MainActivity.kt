package io.bm.aac_example.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import awaitObjectResult
import com.github.kittinunf.fuel.Fuel
import dagger.android.AndroidInjection
import io.bm.aac_example.R
import io.bm.aac_example.api.CoinResultDeserializer
import io.bm.aac_example.api.CoinRouting
import io.bm.aac_example.model.Coin
import io.bm.aac_example.utils.Resource
import io.bm.aac_example.utils.Status
import io.bm.aac_example.vm.CoinViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var coinViewModel: CoinViewModel

    private val coins: MutableList<Coin> = mutableListOf()

    private lateinit var coinAdapter: CoinAdapter

    private val updateCoins = Observer<Resource<Array<Coin>>> { it ->
        when (it.status) {
            Status.SUCCESS -> {
                //refreshCoinsList(it.data ?: emptyArray())
                this@MainActivity.swipe_refresh.isRefreshing = false
            }
            Status.ERROR -> {
                Toast.makeText(this@MainActivity, it.throwable?.message, Toast.LENGTH_SHORT).show()
                this@MainActivity.swipe_refresh.isRefreshing = false
            }
            Status.LOADING -> this@MainActivity.swipe_refresh.isRefreshing = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        init()

    }

    override fun onResume() {
        super.onResume()
        this.coinViewModel.fetchCoins() // déclenchement à chaque retour sur l'activité
        this.swipe_refresh.setOnRefreshListener {
            this.coinViewModel.fetchCoins(); // déclenchement au swipe vers le bas
        }
    }

    override fun onPause() {
        super.onPause()
        this.swipe_refresh.setOnRefreshListener(null)
    }
    private fun init() {
        val layoutManager = LinearLayoutManager(this)
        this.recycler_view_coin.layoutManager = layoutManager
        coinAdapter = CoinAdapter(coins, this)
        this.recycler_view_coin.adapter = coinAdapter
        this.coinViewModel.coins.observe(this, this.updateCoins)
    }


    private fun updateCoins(newCoins: Array<Coin>) {
        coins.clear()
        coins.addAll(newCoins)
        coinAdapter.notifyDataSetChanged()
    }
}