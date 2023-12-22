package dev.haqim.headsup.ui.part

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.haqim.headsup.R
import dev.haqim.headsup.domain.model.Article
import dev.haqim.headsup.ui.theme.HeadsUpTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardNews(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (Article) -> Unit,
    onSave: (Article) -> Unit 
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(article) },
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(fraction = 6f)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .weight(2f)
                    .clip(RoundedCornerShape(8.dp)),
                error = painterResource(id = R.drawable.unavailable),
                placeholder = painterResource(id = R.drawable.news_pl)
            )
            Column(
                modifier = Modifier
                    .padding(3.dp)
                    .weight(5f)
            ) {
                Text(
                    text = article.sourceName,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color.Blue,
                    )
                )
                Text(
                    text = article.title,
                    maxLines = 2,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                    )
                )
            }

            IconButton(
                onClick = {
                    onSave(article)
                },
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize()
                    .padding(horizontal = 0.dp)
            ) {
                if (!article.isSaved){
                    Icon(
                        painterResource(id = R.drawable.ic_bookmark_border_24),
                        contentDescription = "Unsave",
                        tint = Color.Unspecified
                    ) 
                }else{
                    Icon(
                        painterResource(id = R.drawable.ic_bookmark_24),
                        contentDescription = "Save",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }
            
        }
    }
}

@Preview
@Composable
fun CardNews_Preview(){
    HeadsUpTheme {
        CardNews(
            article = Article(
                id = 1,
                sourceId = "google-news",
                sourceName = "Google News",
                author = "SiliconRepublic.com",
                title = "Irish start-up Kerno raises €1.69m for its troubleshooting tech - SiliconRepublic.com",
                description = null,
                url = "https://news.google.com/rss/articles/CBMiUmh0dHBzOi8vd3d3LnNpbGljb25yZXB1YmxpYy5jb20vc3RhcnQtdXBzL2tlcm5vLXNlZWQtZnVuZGluZy10cm91Ymxlc2hvb3RpbmctY2xvdWTSAQA?oc=5",
                urlToImage = null,
                publishedAt = "2023-12-21T08:34:48Z",
                content = null,
                isSaved = false
            ),
            onClick = {},
            onSave = {}
        )
    }
}