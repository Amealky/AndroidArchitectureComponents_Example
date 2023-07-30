package io.bm.aac_example.utils

/*L'intérêt de cette encapsulation est double :

Notification à l'utilisateur de l'état de chargement des données
Gestion des cas d'erreurs HTTP*/

class Resource<T> private constructor(val status: Status, val data: T?, val throwable: Throwable?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, throwable)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}