package com.example.myapplication

import android.graphics.Color
import android.graphics.text.LineBreaker
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.yariksoffice.lingver.Lingver

class TermsFragment : Fragment() {

    companion object;

    private lateinit var viewModel: TermsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root =  inflater.inflate(R.layout.terms_fragment, container, false)
        val language : String = Lingver.getInstance().getLanguage()
        Lingver.getInstance().setLocale(activity as MainActivity,language)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.terms_of_use)
        val textOfTerms = MainActivity.termsText
        for(n in 0 until textOfTerms.size step 2)
        {
            val mainLinearLayout = root.findViewById(R.id.termsLL) as LinearLayout
            val v: View = LayoutInflater.from(root.context).inflate(R.layout.term_layout, null)
            val lLayout: LinearLayout = v as LinearLayout
            val headerTextView : TextView = lLayout.findViewById(R.id.header)
            val termTextView : TextView = lLayout.findViewById(R.id.term)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    termTextView.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
                }
            }
            headerTextView.setTextColor(Color.parseColor("#FF6347"))
            headerTextView.text = textOfTerms[n]
            termTextView.text = textOfTerms[n+1]
            mainLinearLayout.addView(lLayout)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TermsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}