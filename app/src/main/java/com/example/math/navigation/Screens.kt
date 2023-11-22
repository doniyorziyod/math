package com.example.math.navigation

sealed class Screens(val route: String) {
    object Splash : Screens("splash_view")
    object Level: Screens("level_view")
    object Home: Screens("home_view" + "/{level}")
    object Record: Screens("record_view" + "/{correct}" + "/{incorrect}" + "/{level}" + "/{new_record}")
}
