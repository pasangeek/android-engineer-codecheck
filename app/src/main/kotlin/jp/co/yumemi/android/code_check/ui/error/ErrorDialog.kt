package jp.co.yumemi.android.code_check.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import jp.co.yumemi.android.code_check.databinding.FragmentErrorDialogBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ErrorDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class ErrorDialog(private val errorMessage: String) : DialogFragment() {

    private var binding: FragmentErrorDialogBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentErrorDialogBinding.inflate(inflater, container, false)
        val view = binding!!.root

        binding!!.errorMessage.text = errorMessage

        binding!!.closeButton.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clearing the binding reference
        binding = null

    }

}