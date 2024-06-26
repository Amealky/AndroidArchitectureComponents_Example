package io.bm.AndroidArchitectureComponents_Example.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.bm.AndroidArchitectureComponents_Example.R
import io.bm.AndroidArchitectureComponents_Example.model.Coin
import kotlinx.android.synthetic.main.item_coin.view.*

class CoinAdapter(private val coins: List<Coin>, private val context: Context)
    : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CoinAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = this.coins[position]
        val coinName = coin.name
        val coinCode = coin.code
        val coinRank = coin.rank
        val coinPrice = coin.price


        holder.coinHeader.text = context.getString(
            R.string.coin_header,
            coinName,
            coinCode
        )

        holder.coinRank.text = context.getString(
            R.string.coin_rank,
            coinRank
        )

        holder.coinPrice.text = context.getString(
            R.string.coin_price,
            coinPrice
        )
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val coinHeader: TextView = v.coin_header
        val coinRank: TextView = v.coin_rank
        val coinPrice: TextView = v.coin_price
    }
}