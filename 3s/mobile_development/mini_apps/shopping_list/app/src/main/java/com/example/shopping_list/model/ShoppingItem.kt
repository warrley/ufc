data class ShoppingItem(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false
)

data class ShoppingListUiState(
    val items: List<ShoppingItem> = emptyList(),
    val isLoading: Boolean = false
)