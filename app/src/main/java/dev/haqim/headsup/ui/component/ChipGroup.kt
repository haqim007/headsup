package dev.haqim.headsup.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.haqim.headsup.domain.model.Category
import dev.haqim.headsup.domain.model.categories
import dev.haqim.headsup.ui.part.CategoryFilter


@Composable
fun <T> ChipGroup(
    selectedChip: T? = null,
    onSelectedChanged: (T) -> Unit = {},
    content: @Composable (selectedChip: T?, onSelectedChanged: (T) -> Unit) -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        content(
            selectedChip,
            onSelectedChanged
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChipGroup_preview(){
    ChipGroup<Category>(
        selectedChip = null
    ){ selectedChip, onSelectedChanged ->
        LazyRow {
            items(categories) {
                CategoryFilter(
                    text = it.name,
                    modifier = Modifier.padding(end = 8.dp)
                ) {}
            }
        }
    }
}