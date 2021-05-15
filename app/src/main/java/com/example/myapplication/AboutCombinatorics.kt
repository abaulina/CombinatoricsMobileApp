package com.example.myapplication

import android.graphics.text.LineBreaker
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.yariksoffice.lingver.Lingver

class AboutCombinatorics : Fragment() {

    companion object;

    private lateinit var viewModel: AboutCombinatoricsViewModel

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.about_combinatorics_fragment, container, false)
        val language : String = Lingver.getInstance().getLanguage()
        Lingver.getInstance().setLocale(activity as MainActivity,language)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.fragment_aboutFull)
        val listAbout= MainActivity.aboutText
        val v: TextView = root.findViewById(R.id.aboutCombinatorics)
        v.text = listAbout[0]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AboutCombinatoricsViewModel::class.java)
    }

}