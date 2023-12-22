package dev.haqim.headsup.ui.util.navigation

sealed class Screen(val route: String){
    object NewsListScreen: Screen("news_list")
    object NewsDetailScreen: Screen("news_list/{id}/{fromSaved}"){
        fun createRoute(id: String, fromSaved: Boolean = false) = "news_list/$id/$fromSaved"
    }
    object NewsBookmarkListScreen: Screen("news_bookmark_list")
}