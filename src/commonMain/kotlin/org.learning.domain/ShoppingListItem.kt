package org.learning.domain
import kotlinx.serialization.Serializable


@Serializable
data class ShoppingListItem(val description: String, val priority: Int) {
    val id = description.hashCode()

    companion object {
        const val path = "/shoppingList"
    }
}
