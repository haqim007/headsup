package dev.haqim.headsup.ui.part

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.haqim.headsup.ui.theme.HeadsUpTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilter(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    leadingIcon: @Composable() (() -> Unit)? = null,
    onClick: () -> Unit
){

    FilterChip(
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.primary
        ),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
        ),
        modifier = modifier,
        onClick = onClick,
        label = {
            Text(text)
        },
        selected = isSelected,
        leadingIcon = leadingIcon,
    )
}

@Preview()
@Composable
fun CategoryCard_preview(){
    HeadsUpTheme {
        CategoryFilter(
            text = "Sport",
            isSelected = true,
            leadingIcon = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = null,
                    Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        ){}
    }
}
