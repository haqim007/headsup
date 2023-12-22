package dev.haqim.headsup.ui.newsdetail

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
class NewsDetailViewModel @Inject constructor(
    private val repository: INewsRepository
): ViewModel() {
    private val _state = MutableStateFlow(NewsDetailUiState())
    val state = _state.asStateFlow()
    
    
    fun getArticle(id:Int, isFromSaved: Boolean){
        viewModelScope.launch {
           if (!isFromSaved){
               repository.getArticle(id).collectLatest {
                   _state.update { state -> state.copy(article = it) }
               }
           }else{
               repository.getSavedArticle(id).collectLatest {
                   _state.update { state -> state.copy(article = it) }
               }
           }
        }
    }
    
    fun save(article: Article){
        viewModelScope.launch {
            repository.saveArticle(article)
        }
    }
}

data class NewsDetailUiState(
    val article: Article? = null
)