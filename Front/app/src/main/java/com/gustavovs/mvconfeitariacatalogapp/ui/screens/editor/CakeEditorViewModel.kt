package com.gustavovs.mvconfeitariacatalogapp.ui.screens.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavovs.mvconfeitariacatalogapp.data.model.Cake
import com.gustavovs.mvconfeitariacatalogapp.data.repository.CakeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CakeEditorViewModel(
    private val repository: CakeRepository = CakeRepository()
) : ViewModel() {

    var id: Long? by mutableStateOf(null)
        private set

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var slices by mutableStateOf("")
    var price by mutableStateOf("")

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object SaveSuccess : UiEvent()
    }

    fun setCake(cake: Cake?) {
        if (cake != null) {
            id = cake.id
            title = cake.title
            description = cake.description ?: ""
            slices = cake.slices.toString()
            price = cake.price.toString()
        } else {
            id = null
            title = ""
            description = ""
            slices = ""
            price = ""
        }
    }

    fun saveCake() {
        if (title.isBlank()) {
            errorMessage = "O título é obrigatório"
            return
        }
        val slicesInt = slices.toIntOrNull()
        if (slicesInt == null || slicesInt <= 0) {
            errorMessage = "O número de fatias deve ser maior que zero"
            return
        }
        val priceDecimal = price.toBigDecimalOrNull()
        if (priceDecimal == null || priceDecimal <= BigDecimal.ZERO) {
            errorMessage = "O preço deve ser um valor válido e maior que zero"
            return
        }

        errorMessage = null
        isLoading = true

        viewModelScope.launch {
            val cake = Cake(
                id = id,
                title = title.trim(),
                description = description.trim().ifEmpty { null },
                slices = slicesInt,
                price = priceDecimal
            )

            val result = if (id != null) {
                repository.updateCake(id!!, cake)
            } else {
                repository.createCake(cake)
            }

            isLoading = false
            result.onSuccess {
                _eventFlow.emit(UiEvent.SaveSuccess)
            }.onFailure { exception ->
                errorMessage = exception.message ?: "Erro ao salvar bolo"
            }
        }
    }
}
