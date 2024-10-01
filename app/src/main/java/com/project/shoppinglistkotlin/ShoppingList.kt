package com.project.shoppinglistkotlin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp



data class ShoppingItems(val id:Int,
                         var name:String,
                         val quantity:Int,
                         var editing:Boolean = false)

@Composable
fun ShoppingListApp(){
    var sItems by remember{ mutableStateOf( listOf<ShoppingItems>()) }
    var showDialog by remember{ mutableStateOf( false ) }
    var itemName by remember{ mutableStateOf( "" ) }
    var itemQuantity by remember{ mutableStateOf( "1" ) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 25.dp, 0.dp, 0.dp)) {
            Text(text = "Add Items")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            items(sItems){

            }
        }
    }
    
    if (showDialog) {
        AlertDialog(onDismissRequest = { /*TODO*/ },
            confirmButton = {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween) {

                                Button(onClick = {
                                    // storing data in list
                                    if (itemName.isNotEmpty()){
                                        val newItem = ShoppingItems(
                                            id = sItems.size + 1,
                                            name = itemName,
                                            quantity = itemQuantity.toInt(),
                                        )

                                        sItems = sItems + newItem
                                        showDialog = false
                                        itemName = ""
                                        itemQuantity = "1"
                                    }
                                }) {
                                    Text(text = "Add")
                                }

                                Button(onClick = { showDialog = false }) {
                                    Text(text = "Cancel")
                                }

                            }
            },
            title = { Text(text = "Add Shopping Item") },
            text = {
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange = {itemName = it},
                        singleLine = true,
                        label = { Text(text = "Enter Item")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp))

                    OutlinedTextField(value = itemQuantity,
                        onValueChange = {itemQuantity = it},
                        singleLine = true,
                        label = { Text(text = "Enter Quantity")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp))

                }
            }
            )
    }
}
