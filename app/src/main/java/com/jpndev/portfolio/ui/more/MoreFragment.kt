package com.jpndev.portfolio.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import com.jpndev.portfolio.R
import com.jpndev.portfolio.databinding.FragmentHomeBinding
import com.jpndev.portfolio.databinding.FragmentMoreBinding
import com.jpndev.portfolio.ui.home.HomeViewModel
import com.jpndev.portfolio.ui.pmanage.PManageActivity

class MoreFragment  : Fragment() {

    //private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentMoreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


      /*  homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)*/

        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pwdManagerCard.setOnClickListener {
            val intent = Intent(activity, PManageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}