package omurzakov.zerowaste

import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import omurzakov.zerowaste.communication.MockLocalRepositoryImpl
import omurzakov.zerowaste.communication.MockRemoteRepositoryImpl
import omurzakov.zerowaste.utils.Constant.TT_BACK_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_CANCEL_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_CATEGORY_BOTTOM_ITEM
import omurzakov.zerowaste.utils.Constant.TT_CATEGORY_LIST
import omurzakov.zerowaste.utils.Constant.TT_CHOOSE_CATEGORY_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_DIALOG_WINDOW
import omurzakov.zerowaste.utils.Constant.TT_FLOAT_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_HISTORY_LIST
import omurzakov.zerowaste.utils.Constant.TT_MAP_BOTTOM_ITEM
import omurzakov.zerowaste.utils.Constant.TT_MAP_DETAIL_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_SCAN_PHOTO_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_SHEET_CARD
import omurzakov.zerowaste.utils.Constant.TT_SORT_IT_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_SORT_NOW_BUTTON
import omurzakov.zerowaste.ml.MockMachineLearningModelImpl
import omurzakov.zerowaste.navigation.Destination
import omurzakov.zerowaste.navigation.NavGraph
import omurzakov.zerowaste.navigation.NavigationRouterImpl
import omurzakov.zerowaste.ui.activities.MainActivity
import omurzakov.zerowaste.ui.elements.BottomNavigationBar
import omurzakov.zerowaste.ui.elements.SortNowDialog
import omurzakov.zerowaste.ui.screens.camera.CameraViewModel
import omurzakov.zerowaste.ui.screens.category.CategoryViewModel
import omurzakov.zerowaste.ui.screens.detail.DetailViewModel
import omurzakov.zerowaste.ui.screens.historydetail.HistoryDetailViewModel
import omurzakov.zerowaste.ui.screens.home.HomeViewModel
import omurzakov.zerowaste.ui.screens.map.MapViewModel
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class UITests {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    private val testModule = module {
        viewModel { HomeViewModel(MockLocalRepositoryImpl()) }
        viewModel { DetailViewModel(MockRemoteRepositoryImpl(), MockLocalRepositoryImpl()) }
        viewModel { MapViewModel(MockRemoteRepositoryImpl()) }
        viewModel { HistoryDetailViewModel(MockLocalRepositoryImpl()) }
        viewModel { CameraViewModel(MockMachineLearningModelImpl()) }
        viewModel { CategoryViewModel() }
    }

    @Before
    fun setUp() {
        loadKoinModules(testModule)
    }

    @Test
    fun test_floating_button_dialog_window() {
        with(composeRule) {
            //given
            onNodeWithTag(TT_FLOAT_BUTTON, true).assertIsDisplayed()

            //when
            onNodeWithTag(TT_FLOAT_BUTTON).performClick()

            //then
            onNodeWithTag(TT_DIALOG_WINDOW, true).assertIsDisplayed()
            onNodeWithTag(TT_SCAN_PHOTO_BUTTON).assertIsDisplayed()
            onNodeWithTag(TT_CHOOSE_CATEGORY_BUTTON).assertIsDisplayed()
        }
    }

    /** HOME SCREEN **/
    @Test
    fun test_home_screen_dialog_navigates_to_camera_screen() {
        //given
        launchScreenWithNavigation(Destination.HomeScreen.route)
        with(composeRule) {
            onNodeWithTag(TT_SORT_NOW_BUTTON, true).assertIsDisplayed()
            onNodeWithTag(TT_SORT_NOW_BUTTON).performClick()
            onNodeWithTag(TT_DIALOG_WINDOW, true).assertIsDisplayed()

            //when
            onNodeWithTag(TT_SCAN_PHOTO_BUTTON).performClick()
            waitForIdle()

            //then
            Assert.assertEquals(Destination.PhotoScreen.route, currentBackStackEntry())
        }
    }

    @Test
    fun test_home_screen_dialog_navigates_to_category_screen() {
        //given
        launchScreenWithNavigation(Destination.HomeScreen.route)

        with(composeRule) {
            //when
            onNodeWithTag(TT_SORT_NOW_BUTTON, true).assertIsDisplayed()
            onNodeWithTag(TT_SORT_NOW_BUTTON).performClick()
            onNodeWithTag(TT_DIALOG_WINDOW, true).assertIsDisplayed()
            onNodeWithTag(TT_CHOOSE_CATEGORY_BUTTON).performClick()

            //then
            Assert.assertEquals(Destination.ListOfCategory.route, currentBackStackEntry())
        }
    }

    @Test
    fun test_home_screen_list_all_items() {
        //given
        val itemsInDB = MockLocalRepositoryImpl().getAll().value!!.size

        //when + then
        with(composeRule) {
            onNodeWithTag(TT_HISTORY_LIST).onChildren().assertCountEquals(itemsInDB)
        }
    }

    @Test
    fun test_home_navigates_to_history_detail() {
        //given
        launchScreenWithNavigation(Destination.HomeScreen.route)

        with(composeRule) {
            //when
            onNodeWithTag(TT_HISTORY_LIST).onChildren().onFirst().performClick()
            waitForIdle()

            //then
            Assert.assertEquals(
                Destination.HistoryDetail.route + "/{id}",
                currentBackStackEntry()
            )
        }
    }

    /** CATEGORY SCREEN **/
    @Test
    fun test_bottom_item_navigates_to_category_screen() {
        //given
        launchScreenWithBarNavigation(Destination.HomeScreen.route)

        with(composeRule) {
            //when
            onNodeWithTag(TT_CATEGORY_BOTTOM_ITEM, true).assertIsDisplayed()
            onNodeWithTag(TT_CATEGORY_BOTTOM_ITEM).performClick()

            //then
            Assert.assertEquals(Destination.ListOfCategory.route, currentBackStackEntry())
        }
    }

    @Test
    fun test_category_screen_list_all_categories() {
        //given
        launchScreenWithBarNavigation(Destination.HomeScreen.route)

        with(composeRule) {
            onNodeWithTag(TT_CATEGORY_BOTTOM_ITEM, true).assertIsDisplayed()

            //when
            onNodeWithTag(TT_CATEGORY_BOTTOM_ITEM).performClick()

            //then
            onNodeWithTag(TT_CATEGORY_LIST).onChildren().assertCountEquals(8)
        }
    }

    /** MAP SCREEN **/
    @Test
    fun test_bottom_item_navigates_to_map_screen() {
        //given
        launchScreenWithBarNavigation(Destination.HomeScreen.route)

        with(composeRule) {
            onNodeWithTag(TT_MAP_BOTTOM_ITEM, true).assertIsDisplayed()

            //when
            onNodeWithTag(TT_MAP_BOTTOM_ITEM).performClick()

            //then
            Assert.assertEquals(
                Destination.MapScreen.route + "/{category}",
                currentBackStackEntry()
            )
        }
    }

    /** CATEGORY DETAIL SCREEN **/
    @Test
    fun test_category_screen_navigates_to_category_detail_screen() {
        //given
        launchScreenWithNavigation(Destination.ListOfCategory.route)

        with(composeRule) {
            //when
            onNodeWithTag(TT_CATEGORY_LIST).onChildren().onFirst().performClick()

            //then
            Assert.assertEquals(
                Destination.CategoryDetailScreen.route + "/{category}",
                currentBackStackEntry()
            )
        }
    }

    @Test
    fun test_category_detail_open_sheet() {
        //given
        launchScreenWithBarNavigation(Destination.ListOfCategory.route)

        with(composeRule) {
            //when
            onNodeWithTag(TT_CATEGORY_LIST).onChildren().onFirst().performClick()
            onNodeWithTag(TT_SORT_IT_BUTTON, true).assertIsDisplayed()
            onNodeWithTag(TT_SORT_IT_BUTTON).performClick()

            //then
            onNodeWithTag(TT_SHEET_CARD, true).assertIsDisplayed()
        }
    }

    @Test
    fun test_category_detail_open_sheet_cancel() {
        //given
        launchScreenWithBarNavigation(Destination.ListOfCategory.route)
        with(composeRule) {
            onNodeWithTag(TT_CATEGORY_LIST)
                .onChildren()
                .onFirst()
                .performClick()

            onNodeWithTag(TT_SORT_IT_BUTTON, true).assertIsDisplayed()
            onNodeWithTag(TT_SORT_IT_BUTTON).performClick()
            onNodeWithTag(TT_SHEET_CARD, true).assertIsDisplayed()

            //when
            onNodeWithTag(TT_CANCEL_BUTTON).performClick()
            waitForIdle()

            //then
            onNodeWithTag(TT_CANCEL_BUTTON, true).assertIsNotDisplayed()
        }
    }

    @Test
    fun test_category_detail_navigates_to_map() {
        //given
        launchScreenWithNavigation(Destination.ListOfCategory.route)

        with(composeRule) {
            onNodeWithTag(TT_CATEGORY_LIST).onChildren().onFirst().performClick()
            onNodeWithTag(TT_MAP_DETAIL_BUTTON, true).assertIsDisplayed()

            //when
            onNodeWithTag(TT_MAP_DETAIL_BUTTON).performClick()
            waitForIdle()

            Assert.assertEquals(
                Destination.MapScreen.route + "/{category}",
                currentBackStackEntry()
            )
        }
    }

    /** HISTORY DETAIL SCREEN **/
    @Test
    fun test_home_screen_navigates_to_history_detail_screen() {
        //given
        launchScreenWithNavigation(Destination.HomeScreen.route)

        with(composeRule) {
            //when: 1
            onNodeWithTag(TT_HISTORY_LIST).onChildren().onFirst().performClick()
            waitForIdle()

            //then: 1
            Assert.assertEquals(
                Destination.HistoryDetail.route + "/{id}",
                currentBackStackEntry()
            )

            //when: 2
            onNodeWithTag(TT_BACK_BUTTON).performClick()
            waitForIdle()

            //then: 2
            Assert.assertEquals(Destination.HomeScreen.route, currentBackStackEntry())
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun launchScreenWithNavigation(route: String) {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                NavGraph(navController = navController, startDestination = route)
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun launchScreenWithBarNavigation(route: String) {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                val openDialog = remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomAppBar(
                            backgroundColor = Color.Transparent,
                            cutoutShape = CircleShape,
                        ) {
                            BottomNavigationBar(navController = navController)
                        }
                    },
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = {
                        FloatingActionButton(
                            shape = CircleShape,
                            modifier = Modifier.testTag(TT_FLOAT_BUTTON),
                            onClick = { openDialog.value = true }) {
                            Icon(Icons.Filled.Add, "")
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        if (openDialog.value) {
                            SortNowDialog(
                                openDialog,
                                remember { NavigationRouterImpl(navController) })
                        }
                        NavGraph(startDestination = route, navController = navController)
                    }
                }
            }
        }
    }

    private fun currentBackStackEntry(): String? {
        return navController.currentBackStackEntry?.destination?.route
    }

    @After
    fun tearDown() {
        unloadKoinModules(testModule)
    }
}