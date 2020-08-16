package mf.patomessenger.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import mf.patomessenger.R
import kotlin.reflect.KFunction0

class AlertDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(getString(R.string.decline)) { _, _ ->
                negativeFunction?.invoke()
            }
            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                positiveFunction()
            }
            .also {
                if (!hideNeutral) {
                    it.setNeutralButton(getString(R.string.cancel)) { _, _ ->
                        neutralFunction?.invoke()
                    }
                }
            }.create()

    fun display(
        fragmentManager: FragmentManager,
        title: String,
        message: String,
        hideNeutral: Boolean = true,
        tag: String,
        positiveFunction: KFunction0<Unit>,
        negativeFunction: KFunction0<Unit>? = null,
        neutralFunction: KFunction0<Unit>? = null
    ) {
        this.title = title
        this.message = message
        this.hideNeutral = hideNeutral
        this.positiveFunction = positiveFunction
        this.negativeFunction = negativeFunction
        this.neutralFunction = neutralFunction
        return show(fragmentManager, tag)
    }

    private lateinit var title: String
    private lateinit var message: String
    private var hideNeutral: Boolean = true
    private lateinit var positiveFunction: KFunction0<Unit>
    private var negativeFunction: KFunction0<Unit>? = null
    private var neutralFunction: KFunction0<Unit>? = null

}

