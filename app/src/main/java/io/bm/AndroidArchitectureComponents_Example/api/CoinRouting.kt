package io.bm.AndroidArchitectureComponents_Example.api

import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.util.FuelRouting
import io.bm.AndroidArchitectureComponents_Example.BuildConfig


sealed class CoinRouting : FuelRouting {

    override val basePath = BuildConfig.API_BASE_URL

    class GetCoins(
        override val bytes: ByteArray? = ByteArray(0),
        override val body: String? = "") : CoinRouting()

    override val method: Method
        get() {
            when(this) {
                is GetCoins -> return Method.GET
            }
        }

    override val path: String
        get() {
            return when(this) {
                is GetCoins -> "cryptocurrency/listings/latest?CMC_PRO_API_KEY=" + BuildConfig.API_KEY
            }
        }

    override val params: List<Pair<String, Any?>>?
        get() {
            return when(this) {
                is GetCoins -> listOf("structure" to "array")
            }
        }

    override val headers: Map<String, String>?
        get() {
            return null
        }

}