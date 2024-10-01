package com.project.shoppinglistkotlin

import androidx.compose.ui.graphics.Color

// Map Keyword
fun MapKeyword() {

    val number = listOf(1,2,3)
    val doubleNumber = number.map { it * 2 }
    println(number)
    println(doubleNumber)

}

// Copy Keyword
fun CopyKeyword() {
    val blueRoseVase = Vase(color = "Blue", design = "Rose")
    val redRoseVase = blueRoseVase.copy(color = "Red")
    println(blueRoseVase)
    println(redRoseVase)
}

data class Vase(val color: String, val design:String)

//Let Keyword
fun Letkeyword(){

    // Nullable String ( string can be empty and also we can use the data without changing its properties)
    val name : String? = "Ella"
    name?.let {
        println(it.toUpperCase())
    }
}