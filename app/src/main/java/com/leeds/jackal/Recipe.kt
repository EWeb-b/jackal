package com.leeds.jackal

//Data class for creating recipes

data class Recipe (var recipeName:String = "", var serves: Int = 0, var ingredients:ArrayList<String> = ArrayList<String>())