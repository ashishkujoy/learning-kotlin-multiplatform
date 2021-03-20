package org.learning.adaptor.repository

import org.learning.domain.ShoppingListItem

class ShoppingListRepository {
    private val shoppingListItems = mutableListOf(
        ShoppingListItem("Soap", 1),
        ShoppingListItem("Biscuit", 1),
        ShoppingListItem("Shoe", 10)
    )

    fun getShoppingList(): List<ShoppingListItem> = shoppingListItems.toList()

    fun add(newItem: ShoppingListItem) {
        shoppingListItems.add(newItem)
    }

    fun removeItemById(itemId: Int) {
        shoppingListItems.removeIf { it.id == itemId }
    }
}