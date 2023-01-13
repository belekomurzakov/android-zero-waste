package omurzakov.zerowaste.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import omurzakov.zerowaste.navigation.Destination
import omurzakov.zerowaste.navigation.NavGraph
import omurzakov.zerowaste.navigation.NavigationRouterImpl
import omurzakov.zerowaste.ui.elements.BottomNavigationBar
import omurzakov.zerowaste.ui.elements.SortNowDialog
import omurzakov.zerowaste.ui.theme.ZeroWasteTheme
import omurzakov.zerowaste.utils.Constant.TT_FLOAT_BUTTON

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            ZeroWasteTheme {
                val navController = rememberNavController()
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
                        NavGraph(
                            startDestination = Destination.HomeScreen.route,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}