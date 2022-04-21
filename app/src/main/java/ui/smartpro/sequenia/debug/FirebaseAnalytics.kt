package ui.smartpro.sequenia.debug

import android.content.Context
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalytics(private val context: Context) {

   var firebaseAnalytics = FirebaseAnalytics.getInstance(context)

   private fun sendEvent(action: String, text: String) {
      val params = bundleOf("message" to text)
      firebaseAnalytics.logEvent(action, params)
   }

   fun choiseGenre(action: String, text: String) {
      sendEvent(action, text)
   }

   fun choiseFilm(action: String, text: String) {
      sendEvent(action, text)
   }
}