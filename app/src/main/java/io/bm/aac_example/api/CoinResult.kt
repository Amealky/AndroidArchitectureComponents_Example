package io.bm.aac_example.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.bm.aac_example.model.Coin
import java.util.*

data class CoinResult (
    @Expose
    @SerializedName("data")
    val data: Array<Coin>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoinResult

        if (!Arrays.equals(data, other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(data)
    }
}