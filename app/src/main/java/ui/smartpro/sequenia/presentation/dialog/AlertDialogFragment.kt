package ui.smartpro.sequenia.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import ui.smartpro.sequenia.R

class AlertDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        requireContext().let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.alert_message))
                .setMessage(R.string.alert_internet_connections)
                .setPositiveButton(getString(R.string.connect_internet)) { dialog, button ->
                    (requireActivity() as AppCompatActivity).onBackPressed()
                }
                .setNegativeButton(getString(R.string.no)) { dialog, button ->
                    (requireActivity() as AppCompatActivity).onBackPressed()
                }
                .setNeutralButton(getString(R.string.cancel)) { dialog, button ->
                    (requireActivity() as AppCompatActivity).onBackPressed()
                }
                .create()
        }

    companion object {
        const val TAG = "AlertDialogFragment"
    }
}