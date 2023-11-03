package jp.co.yumemi.android.code_check.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.co.yumemi.android.code_check.R

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentFollowing.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentFollowing : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

}