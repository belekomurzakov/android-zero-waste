package omurzakov.zerowaste.models

import omurzakov.zerowaste.R
import omurzakov.zerowaste.utils.Constant.TT_CATEGORY_BOTTOM_ITEM
import omurzakov.zerowaste.utils.Constant.TT_HOME_BOTTOM_ITEM
import omurzakov.zerowaste.utils.Constant.TT_MAP_BOTTOM_ITEM

sealed class BottomNavItem(
    var title: String,
    var icon: Int,
    var route: String,
    var testTag: String
) {
    object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        route = "home",
        testTag = TT_HOME_BOTTOM_ITEM
    )

    object Categories : BottomNavItem(
        title = "Categories",
        icon = R.drawable.ic_bullet,
        route = "list_of_category",
        testTag = TT_CATEGORY_BOTTOM_ITEM
    )

    object Map : BottomNavItem(
        title = "Map",
        icon = R.drawable.ic_map,
        route = "map/no-category",
        testTag = TT_MAP_BOTTOM_ITEM
    )
}