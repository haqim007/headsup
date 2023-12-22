package dev.haqim.headsup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.haqim.headsup.ui.component.PlainMessage
import dev.haqim.headsup.ui.newsbookmarklist.SavedNewsScreen
import dev.haqim.headsup.ui.newsdetail.NewsDetailScreen
import dev.haqim.headsup.ui.newslists.NewsListScreen
import dev.haqim.headsup.ui.theme.HeadsUpTheme
import dev.haqim.headsup.ui.util.navigation.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeadsUpTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HeadsUpApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadsUpApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (currentRoute != Screen.NewsDetailScreen.route) {
                        Row {
                            Icon(
                                painterResource(id = R.drawable.ic_newspaper_24),
                                contentDescription = null,
                                modifier = Modifier.padding(end = 5.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = stringResource(id = 
                                    if (currentRoute == Screen.NewsListScreen.route){
                                        R.string.headsup
                                    }else{
                                        R.string.saved_news
                                    }),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (currentRoute != Screen.NewsListScreen.route) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Go Back"
                            )
                        }
                        
                    }
                },
                actions = {
                    if (currentRoute == Screen.NewsListScreen.route){
                        IconButton(onClick = {
                            navController.navigate(
                                Screen.NewsBookmarkListScreen.route
                            )
                        }) {
                            Icon(
                                painterResource(id = R.drawable.ic_bookmarks_24),
                                contentDescription = "See bookmarks",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

//                    IconButton(
//                        onClick = {},
//                        modifier = Modifier
//                            .size(40.dp)
//                            .padding(end = 8.dp)
//
//                    ) {
//                        Icon(
//                            painterResource(id = R.drawable.indonesia_flag),
//                            contentDescription = "Choose source",
//                            tint = Color.Unspecified
//                        )
//                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController, 
            startDestination = Screen.NewsListScreen.route, 
            modifier = modifier.padding(paddingValues)
        ){
            composable(Screen.NewsListScreen.route){
                NewsListScreen(navController = navController)
            }
            composable(
                Screen.NewsDetailScreen.route,
                arguments = listOf(
                    navArgument(ID){type = NavType.StringType},
                    navArgument(FROM_SAVED){type = NavType.BoolType}
                )
            ){
                val id = it.arguments?.getString(ID)
                val isFromSaved = it.arguments?.getBoolean(FROM_SAVED) ?: false
                if (id != null) {
                    NewsDetailScreen(id = id.toInt(), isFromSaved = isFromSaved)
                }else{
                    PlainMessage(message = "ID not found")
                }
            }
            composable(Screen.NewsBookmarkListScreen.route){
                SavedNewsScreen(navController = navController)
            }
        }
    }
}
private const val ID = "id"
private const val FROM_SAVED = "fromSaved"

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HeadsUpTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HeadsUpApp()
        }
    }
}