package dev.haqim.headsup.ui.newsbookmarklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.haqim.headsup.domain.model.Article
import dev.haqim.headsup.domain.model.Category
import dev.haqim.headsup.domain.model.categories
import dev.haqim.headsup.domain.repository.INewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val repository: INewsRepository
): ViewModel() {
    private val _state = MutableStateFlow(SavedNewsUiState())
    val state = _state.asStateFlow()
    private val _pagingFlow = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val pagingDataFlow: Flow<PagingData<Article>> = _pagingFlow.asStateFlow()
    
    init {
        viewModelScope.launch {
            repository.getSavedArticles(state.value.query)
                .cachedIn(viewModelScope)
                .collectLatest { 
                    _pagingFlow.emit(it)
                }
        }
        
        viewModelScope.launch { 
            state.map { it.query }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    repository
                        .getSavedArticles(query = query)
                        .cachedIn(viewModelScope)
                }.collectLatest { 
                    _pagingFlow.emit(it)
                }
        }
    }
    
    fun setQuery(query: String){
        _state.update { state ->
            state.copy(query = query.ifEmpty { null })
        }
    }
    
    fun save(article: Article){
        viewModelScope.launch {
            repository.saveArticle(article)
            repository.getSavedArticles(state.value.query)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _pagingFlow.emit(it)
                }
        }
    }
}

data class SavedNewsUiState(
    val query: String? = null,
)