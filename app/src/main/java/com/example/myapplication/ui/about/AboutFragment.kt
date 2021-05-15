package com.example.myapplication.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.yariksoffice.lingver.Lingver


class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel

    companion object;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutViewModel =
                ViewModelProvider(this).get(AboutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val language : String = Lingver.getInstance().getLanguage()
        Lingver.getInstance().setLocale(activity as MainActivity,language)
        val versionNum = 2.0
        val textView: TextView = root.findViewById(R.id.textViewV)
        aboutViewModel.text.observe(viewLifecycleOwner, {
            textView.text = versionNum.toString()
        })

        val btnExtraAbout : Button = root.findViewById(R.id.buttonAbout)
        btnExtraAbout.setOnClickListener {
            val action = AboutFragmentDirections.actionNavAboutToAboutCombinatorics2()
            findNavController().navigate(action)
        }

        val btnToTerms : Button = root.findViewById(R.id.buttonTerms)
        btnToTerms.setOnClickListener {
            val action = AboutFragmentDirections.actionNavAboutToTermsFragment()
            findNavController().navigate(action)
        }
        return root
    }
}