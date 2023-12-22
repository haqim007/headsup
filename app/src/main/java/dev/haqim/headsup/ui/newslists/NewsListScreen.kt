package dev.haqim.headsup.ui.newslists

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.haqim.headsup.domain.model.Article
import dev.haqim.headsup.domain.model.Category
import dev.haqim.headsup.domain.model.categories
import dev.haqim.headsup.domain.model.dummyArticles
import dev.haqim.headsup.ui.component.ChipGroup
import dev.haqim.headsup.ui.part.CardNews
import dev.haqim.headsup.ui.part.CategoryFilter
import dev.haqim.headsup.ui.util.navigation.Screen
import kotlinx.coroutines.flow.flowOf

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    viewModel: NewsListViewModel = hiltViewModel(),
){
    val pagingData = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val uiState by viewModel.state.collectAsState()
    NewsListContent(
        pagingData,
        modifier,
        selectedCategory = uiState.category,
        onClick = {article ->
            navController?.navigate(
                Screen.NewsDetailScreen.createRoute(article.id.toString())
            )
        },
        onCategoryChange = {category -> 
            viewModel.setCategory(category)
        }
    ) { article -> viewModel.save(article) }
}

@Composable
private fun NewsListContent(
    pagingData: LazyPagingItems<Article>,
    modifier: Modifier = Modifier,
    selectedCategory: Category?,
    onClick: (Article) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onSave: (Article) -> Unit
) {
    Column(modifier = modifier) {
        ChipGroup(
            selectedChip = selectedCategory
        ) { _, _ ->
            LazyRow {
                items(categories) {
                    CategoryFilter(
                        text = it.name,
                        isSelected = selectedCategory == it,
                        modifier = Modifier.padding(end = 8.dp),
                        leadingIcon = {
                            Icon(
                                painterResource(id = it.icon),
                                tint = if (selectedCategory == it) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )
                        }
                    ) {
                        onCategoryChange(it)
                    }
                }
            }
        }
        Column(modifier = modifier) {
            if (pagingData.loadState.refresh is LoadState.Loading) {
                if (pagingData.itemCount < 1) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            } else if (pagingData.loadState.refresh is LoadState.Error) {
                OnLoadError(pagingData.loadState, pagingData)
            } else if (
                pagingData.loadState.refresh is LoadState.NotLoading &&
                pagingData.itemCount == 0
            ) { // empty?
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text("List is empty")
                }

            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = rememberLazyListState()
            ) {

                items(
                    count = pagingData.itemCount,
                    key = pagingData.itemKey(),
                    contentType = pagingData.itemContentType()
                ) { index ->
                    val item = pagingData[index]
                    item?.let {
                        CardNews(
                            article = item,
                            onClick = {
                               onClick(it)
                            },
                            onSave = {
                                onSave(it)
                            },
                        )
                    }
                }

            }

            if (pagingData.loadState.append is LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (pagingData.loadState.append is LoadState.Error) {
                OnLoadError(pagingData.loadState, pagingData)
            }

        }
    }
}


@Composable
private fun OnLoadError(
    loadState: CombinedLoadStates,
    pagingData: LazyPagingItems<Article>,
) {
    val isPaginatingError = (loadState.append is LoadState.Error) ||
    pagingData.itemCount > 1
    val error = if (loadState.append is LoadState.Error)
        (loadState.append as LoadState.Error).error
    else
        (loadState.refresh as LoadState.Error).error

    val modifierError = if (isPaginatingError) {
        Modifier.padding(8.dp)
    } else {
        Modifier.fillMaxSize()
    }
    if (pagingData.itemCount == 0) {
        Column(
            modifier = modifierError,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!isPaginatingError) {
                Icon(
                    modifier = Modifier
                        .size(64.dp),
                    imageVector = Icons.Rounded.Warning, contentDescription = null
                )
            }

            Log.e("news list", error.stackTraceToString())

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = error.message ?: error.toString(),
                textAlign = TextAlign.Center,
            )

            Button(
                onClick = {
                    pagingData.refresh()
                },
                content = {
                    Text(text = "Refresh")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                )
            )
        }
    }
}

@Preview
@Composable
private fun NewsListScreen_preview(){
    NewsListContent(
        pagingData = flowOf(
            PagingData.from(
                dummyArticles.toList()
            )
        ).collectAsLazyPagingItems(),
        selectedCategory = categories[0],
        onClick = {},
        onSave = {},
        onCategoryChange = {}
    )
}