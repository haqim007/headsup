package dev.haqim.headsup.ui.newslists

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: INewsRepository
): ViewModel() {
    private val _state = MutableStateFlow(NewsListUiState())
    val state = _state.asStateFlow()
    private val _pagingFlow = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val pagingDataFlow: Flow<PagingData<Article>> = _pagingFlow.asStateFlow()
    
    init {
        viewModelScope.launch {
            repository.getArticles(state.value.query, state.value.category)
                .cachedIn(viewModelScope)
        }
        
        viewModelScope.launch { 
            state.map { it.query }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    repository
                        .getArticles(query = query, state.value.category)
                        .cachedIn(viewModelScope)
                }.collectLatest { 
                    _pagingFlow.emit(it)
                }
        }

        viewModelScope.launch {
            state.map { it.category }
                .distinctUntilChanged()
                .flatMapLatest { category ->
                    repository
                        .getArticles(query = state.value.query, category)
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
    
    fun setCategory(category: Category){
        _state.update { state ->
            state.copy(category = category)
        }
    }
    
    fun save(article: Article){
        viewModelScope.launch {
            repository.saveArticle(article)
        }
    }
}

data class NewsListUiState(
    val query: String? = null,
    val category: Category = categories[0]
)