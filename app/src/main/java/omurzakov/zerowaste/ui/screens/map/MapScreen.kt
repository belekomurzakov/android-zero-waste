package omurzakov.zerowaste.ui.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.GridBasedAlgorithm
import com.google.maps.android.compose.*
import omurzakov.zerowaste.R
import omurzakov.zerowaste.map.CustomMapRenderer
import omurzakov.zerowaste.map.MarkerUtil
import omurzakov.zerowaste.models.Can
import omurzakov.zerowaste.navigation.INavigationRouter
import omurzakov.zerowaste.ui.elements.BackArrowBar
import omurzakov.zerowaste.ui.elements.ErrorScreen
import omurzakov.zerowaste.ui.elements.LoadingScreen
import omurzakov.zerowaste.ui.elements.ScreenState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navigation: INavigationRouter, viewModel: MapViewModel = getViewModel(), category: String
) {
    val screenState: MutableState<ScreenState<List<Can?>>> =
        rememberSaveable { mutableStateOf(ScreenState.Loading) }

    val disableReclustering = remember { mutableStateOf(false) }

    navigation.getNavController().addOnDestinationChangedListener { controller, destination, _ ->
        if (destination.displayName != controller.currentDestination?.displayName.toString()) {
            disableReclustering.value = true
        }
    }

    viewModel.mapUiState.value.let {
        when (it) {
            is MapUiState.Start -> {
                viewModel.getData(category)
            }
            is MapUiState.Error -> screenState.value = ScreenState.Error(it.error)
            is MapUiState.Success -> {
                screenState.value = ScreenState.DataLoaded(it.data)
            }
        }
    }

    Scaffold(
        topBar = {
            if (category != "no-category") {
                BackArrowBar(
                    topBarText = stringResource(R.string.back),
                    onBackClick = { navigation.returnBack() },
                    actions = {}
                )
            }
        },
        content = {
            MapContent(
                screenState = screenState.value,
                disableReclustering = disableReclustering.value,
                viewModel = viewModel
            )
        },
    )
}

@Composable
fun MapContent(
    screenState: ScreenState<List<Can?>>, disableReclustering: Boolean, viewModel: MapViewModel
) {
    screenState.let {
        when (it) {
            is ScreenState.DataLoaded -> MapDetail(
                containers = it.data, disableReclustering, viewModel
            )
            is ScreenState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is ScreenState.Loading -> LoadingScreen()
        }
    }
}

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapDetail(containers: List<Can?>, disableReclustering: Boolean, viewModel: MapViewModel) {

    val mapUiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = false, mapToolbarEnabled = false))
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(49.62352578743681, 15.346186434122867), 5f)
    }

    var currentMarker by remember { mutableStateOf<Marker?>(null) }
    var clusterManager by remember { mutableStateOf<ClusterManager<Can>?>(null) }
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var clusterRenderer by remember { mutableStateOf<CustomMapRenderer?>(null) }

    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            googleMap?.clear()
        }
    }

    val nonNullContainers = containers.filterNotNull()


    if (nonNullContainers.isNotEmpty() && !disableReclustering) {
        clusterManager?.addItems(nonNullContainers)
        clusterManager?.cluster()
    }


    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxHeight(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ) {
            MapEffect(containers) { map ->
                if (googleMap == null) {
                    googleMap = map
                }

                if (clusterManager == null) {
                    clusterManager = ClusterManager<Can>(context, map)
                }

                clusterRenderer =
                    CustomMapRenderer(context, map, clusterManager!!, viewModel.selectedPlace)

                clusterManager?.apply {
                    algorithm = GridBasedAlgorithm()
                    renderer = clusterRenderer

                    renderer?.setOnClusterItemClickListener { /*item ->
                        if (currentMarker != null) {
                            currentMarker!!.setIcon(
                                BitmapDescriptorFactory.fromBitmap(
                                    MarkerUtil.createCustomMarkerFromLayout(
                                        context, viewModel.selectedPlace!!, false
                                    )
                                )
                            )
                            currentMarker = null
                        }

                        currentMarker = clusterRenderer?.getMarker(item)
                        if (currentMarker != null) {
                            currentMarker!!.setIcon(
                                BitmapDescriptorFactory.fromBitmap(
                                    MarkerUtil.createCustomMarkerFromLayout(
                                        context, viewModel.selectedPlace!!, true
                                    )
                                )
                            )
                        }
                        */
                        true
                    }
                }

                map.setOnCameraIdleListener { clusterManager?.cluster() }

                map.setOnMapClickListener {
                    if (currentMarker != null && viewModel.selectedPlace != null) {
                        currentMarker!!.setIcon(
                            BitmapDescriptorFactory.fromBitmap(
                                MarkerUtil.createBitmapBasedOnType(context))
                        )
                        currentMarker = null
                        viewModel.selectedPlace = null
                    }
                }
            }
        }
    }
}