package com.example.myapplication

class FormulaItem(imageResource: Int, quote: String?, formula: String?, type:Int, description:String) {
    private var mQuote: String? = quote
    private var mFormula: String? = formula
    private var mImageResource : Int = imageResource
    private var mType : Int = type
    private var mLetter : String = description

    fun getQuote(): String? {
        return mQuote
    }

    fun getFormula(): String? {
        return mFormula
    }
    fun getImageResource() : Int{
        return mImageResource
    }
    fun getType():Int{
        return mType
    }
    fun getDescription() : String{
        return mLetter
    }
}