package com.example.myapplication.ui.formulas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.yariksoffice.lingver.Lingver


class FormulasFragment : Fragment() {

    private lateinit var formulasViewModel: FormulasViewModel
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : RecyclerView.Adapter<FormulaAdapter.FormulaViewHolder>
    private lateinit var mLayoutManager : RecyclerView.LayoutManager

    companion object;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_formulas, container, false)
        val language : String = Lingver.getInstance().getLanguage()
        Lingver.getInstance().setLocale(activity as MainActivity,language)
        formulasViewModel =
                ViewModelProvider(this).get(FormulasViewModel::class.java)
        //записываем все строки файла в listFormulas
        val listFormulas= MainActivity.lineList
        val formulaList = ArrayList<FormulaItem>()
        for(n in 0 until listFormulas.size step 4)
        {
            formulaList.add(FormulaItem(R.drawable.ic_calculate,listFormulas[n], listFormulas[n + 1],listFormulas[n+2].toInt(),listFormulas[n+3]))
        }
        mRecyclerView = root.findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(root.context)
        mAdapter = FormulaAdapter(formulaList)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        val searchView = root.findViewById(R.id.search_bar) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(MainActivity.lineList.contains(query))
                    (mAdapter as FormulaAdapter).filter.filter(query)
                else Toast.makeText(root.context, "No match found", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (mAdapter as FormulaAdapter).filter.filter(newText)
                return false
            }
        })
        return root
    }
}


