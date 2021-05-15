package com.example.myapplication.ui.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.yariksoffice.lingver.Lingver


class ExamplesFragment : Fragment() {

    private lateinit var examplesViewModel: ExamplesViewModel
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : RecyclerView.Adapter<ExamplesAdapter.ExamplesViewHolder>
    private lateinit var mLayoutManager : RecyclerView.LayoutManager


    companion object;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_examples, container, false)
        val language : String = Lingver.getInstance().getLanguage()
        Lingver.getInstance().setLocale(activity as MainActivity,language)
        examplesViewModel =
                ViewModelProvider(this).get(ExamplesViewModel::class.java)
        val listExamples = MainActivity.exampleList
        val currentList = ArrayList<ExamplesItem>()
        for(n in 0 until listExamples.size step 2)
        {
           currentList.add(ExamplesItem(listExamples[n], listExamples[n + 1]))
        }
        mRecyclerView = root.findViewById(R.id.recyclerViewExample)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(root.context)
        mAdapter = ExamplesAdapter(currentList)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        val searchView = root.findViewById(R.id.search_bar) as SearchView
        val searchHint : String = getString(R.string.searchHint)
        searchView.queryHint = searchHint
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(MainActivity.exampleList.contains(query))
                    (mAdapter as ExamplesAdapter).filter?.filter(query)
                else Toast.makeText(root.context, "No match found", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (mAdapter as ExamplesAdapter).filter?.filter(newText)
                return false
            }
        })
        return root
    }
}