import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShoppingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingListUiState())
    val itemsL: StateFlow<ShoppingListUiState> get() = _uiState.asStateFlow()

    fun addItem(name: String) {
        if (name.isNotBlank()) {
            _uiState.update { currentState ->
                val newItem = ShoppingItem(currentState.items.size + 1, name = name)
                currentState.copy(
                    items = currentState.items + newItem
                )
            }
        }
    }

    fun toggleItem(item: ShoppingItem) {
        _uiState.update { currentState ->
            val updatedItems = currentState.items.map { currentItem ->
                if (currentItem.id == item.id) {
                    currentItem.copy(isChecked = !currentItem.isChecked)
                } else {
                    currentItem
                }
            }

            currentState.copy(items = updatedItems)
        }
    }
}