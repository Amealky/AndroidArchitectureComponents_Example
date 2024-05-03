package io.bm.AndroidArchitectureComponents_Example.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import io.bm.AndroidArchitectureComponents_Example.R
import io.bm.AndroidArchitectureComponents_Example.model.Coin
import io.bm.AndroidArchitectureComponents_Example.utils.Resource
import io.bm.AndroidArchitectureComponents_Example.utils.Status
import io.bm.AndroidArchitectureComponents_Example.vm.CoinViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var coinViewModel: CoinViewModel

    private val coins: MutableList<Coin> = mutableListOf()

    private lateinit var coinAdapter: CoinAdapter

    private val updateCoins = Observer<Resource<Array<Coin>>> { it ->
        when (it.status) {
            Status.SUCCESS -> {
                updateCoins(it.data ?: emptyArray())
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