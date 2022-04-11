package ui.smartpro.sequenia.data.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

interface NetworkStateProvider {
    fun isNetworkAvailable(): Boolean
    val isAvailable : Boolean
}

class NetworkStateProviderImpl (val context: Context) : NetworkStateProvider{
    var result = false
   private val bsConnected = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isNetworkAvailable(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bsConnected?.run {
                bsConnected.getNetworkCapabilities(bsConnected.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            bsConnected?.run {
                bsConnected.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    override val isAvailable: Boolean
        get()  {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return bsConnected.activeNetwork != null
            } else {
                return bsConnected.activeNetworkInfo != null
            }
        }
}

