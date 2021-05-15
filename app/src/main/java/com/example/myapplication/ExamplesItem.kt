package com.example.myapplication

class ExamplesItem (exercise : String?, solution: String?) {
    private var _exercise: String? = exercise
    private var _solution: String? = solution

    fun getExercise(): String? {
        return _exercise
    }

    fun getSolution(): String? {
        return _solution
    }
}