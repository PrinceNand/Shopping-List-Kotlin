package com.project.shoppinglistkotlin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



data class ShoppingItems(val id:Int,
                         var name:String,
                         var quantity:Int,
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
                itemIterator ->
                if (itemIterator.editing){
                    // edit item
                    ShoppingItemEditor(item = itemIterator , onEditComplete = {
                            editedName, editedQuantity ->

                        // editing is done and changing only editing using copy keyword
                        sItems = sItems.map { it.copy(editing = false) }

                        // find item id and update data
                        val editedItem = sItems.find { it.id == itemIterator.id }

                        //let keyword change the required value in any place without changing full list
                        editedItem?.let {
                            it.name = editedName
                            it.quantity = editedQuantity
                        }

                    })
                } else {
                    // show items
                    ShoppingListItems(item =itemIterator ,
                        onEditClick = {
                            // finding which item we are clicking to edit
                            sItems = sItems.map{ it.copy(editing = it.id == itemIterator.id)}
                        }, onDeleteClick = {
                            // deleting item which is selected in iterator
                            sItems = sItems - itemIterator
                    })
                }
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

@Composable
fun ShoppingListItems(item: ShoppingItems,
                      onEditClick: () -> Unit,
                      onDeleteClick: () -> Unit,
                      ) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(0XFF0118786)),
                shape = RoundedCornerShape(20)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))

        Text(text = "Qty: ${item.quantity}", modifier = Modifier.padding(8.dp))

        Row(modifier = Modifier.padding(8.dp)) {
            // lambda operation don't require {} in onclick
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Button")
            }

            // lambda operation don't require {} in onclick
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Button")
            }
        }

    }
}

@Composable
fun ShoppingItemEditor(item: ShoppingItems,
                       onEditComplete: (String, Int) -> Unit) {

    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.editing) }

    Row(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

        Column {
            BasicTextField(value = editedName,
                onValueChange = {editedName = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp))

            BasicTextField(value = editedQuantity,
                onValueChange = {editedQuantity = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp))
        }

        Button(onClick = {
            isEditing = false
            onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
        }) {
            Text(text = "Save")
        }

    }
}
