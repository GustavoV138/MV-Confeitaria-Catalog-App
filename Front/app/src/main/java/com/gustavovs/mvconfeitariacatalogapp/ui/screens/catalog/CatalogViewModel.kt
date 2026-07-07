package com.gustavovs.mvconfeitariacatalogapp.ui.screens.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavovs.mvconfeitariacatalogapp.data.model.Cake
import com.gustavovs.mvconfeitariacatalogapp.data.repository.CakeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class CatalogViewModel(
    private val repository: CakeRepository = CakeRepository()
) : ViewModel() {

    var cakes by mutableStateOf<List<Cake>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var searchJob: Job? = null

    init {
        loadCakes()
    }

    fun loadCakes() {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            val result = if (searchQuery.isBlank()) {
                repository.getCakes()
            } else {
                repository.searchCakes(searchQuery.trim())
            }
            isLoading = false
            result.onSuccess { list ->
                cakes = list
            }.onFailure { exception ->
                errorMessage = exception.message ?: "Erro ao carregar catálogo"
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // Debounce search calls
            loadCakes()
        }
    }

    fun deleteCake(id: Long) {
        viewModelScope.launch {
            repository.deleteCake(id).onSuccess {
                loadCakes()
            }.onFailure { exception ->
                errorMessage = exception.message ?: "Erro ao excluir bolo"
            }
        }
    }

    fun getFormattedCatalogText(): String {
        if (cakes.isEmpty()) return "Nenhum bolo cadastrado no catálogo atualmente."

        val sb = StringBuilder()
        sb.append("🍰 -MV CONFEITARIA - NOSSO CARDÁPIO- 🍰\n\n")
        sb.append("Olá! Veja as nossas deliciosas opções de bolos disponíveis:\n\n")

        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        cakes.forEach { cake ->
            sb.append("${cake.title} - (Rende até ${cake.slices} fatias.)\n")
            if (!cake.description.isNullOrBlank()) {
                sb.append("📝 - ${cake.description}\n")
            }
            sb.append("💵 Preço: ${currencyFormatter.format(cake.price)}\n")
            sb.append("----------------------------\n\n")
        }

        sb.append("Faça seu pedido conosco! ✨")
        return sb.toString()
    }
}
