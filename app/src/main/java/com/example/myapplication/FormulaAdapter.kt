package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.formulas.FormulasFragmentDirections
import io.github.kexanie.library.MathView
import java.util.*


class FormulaAdapter(private val mExampleList: ArrayList<FormulaItem>) :
    RecyclerView.Adapter<FormulaAdapter.FormulaViewHolder>(), Filterable {
    private val exampleListFull: ArrayList<FormulaItem> = ArrayList(mExampleList)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormulaViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.formula_layout, parent, false)
        return FormulaViewHolder(v)
    }

    override fun onBindViewHolder(holder: FormulaViewHolder, position: Int) {
        val curItem = mExampleList[position]
        holder.mMathView.text = curItem.getFormula()
        holder.mTextView.text = curItem.getQuote()
        if (curItem.getType() == 2) {
            holder.mCalcButton.visibility = View.INVISIBLE
        }
        else holder.mCalcButton.visibility = View.VISIBLE
        holder.mCalcButton.setImageResource(curItem.getImageResource())
        holder.mCalcButton.setOnClickListener {
            val action = FormulasFragmentDirections.actionNavFormulasToCalculationFragment(
                curItem.getDescription(),
                curItem.getType()
            )
            it.findNavController().navigate(action)
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
            val filteredList: MutableList<FormulaItem> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(exampleListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in exampleListFull) {
                    if (item.getQuote()?.toLowerCase(Locale.ROOT)!!.contains(filterPattern)) {
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
            mExampleList.addAll(results.values as List<FormulaItem>)
            notifyDataSetChanged()
        }
    }

    class FormulaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView = itemView.findViewById(R.id.quote)
        var mMathView: MathView = itemView.findViewById(R.id.formulaText)
        var mCalcButton: ImageButton = itemView.findViewById(R.id.startCalculation)
    }

}
