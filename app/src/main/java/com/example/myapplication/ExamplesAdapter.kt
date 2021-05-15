package com.example.myapplication

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.kexanie.library.MathView
import java.util.*
import kotlin.collections.ArrayList


class ExamplesAdapter (private val mExampleList: ArrayList<ExamplesItem>) : RecyclerView.Adapter<ExamplesAdapter.ExamplesViewHolder>(), Filterable {

    private var exampleListFull: ArrayList<ExamplesItem>? = ArrayList(mExampleList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamplesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.example_layout, parent, false)
        return ExamplesViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExamplesViewHolder, position: Int) {
        val curItem = mExampleList[position]
        holder.mMathView.text = curItem.getSolution()
        holder.mTextView.text = curItem.getExercise()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                holder.mTextView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
            }
        }
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<ExamplesItem> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(exampleListFull!!)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in exampleListFull!!) {
                    if (item.getExercise()?.toLowerCase(Locale.ROOT)!!.contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            mExampleList.clear()
            mExampleList.addAll(results.values as List<ExamplesItem>)
            notifyDataSetChanged()
        }
    }


    class ExamplesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView = itemView.findViewById(R.id.expandable_text)
        var mMathView: MathView = itemView.findViewById(R.id.mvExp)
    }
}