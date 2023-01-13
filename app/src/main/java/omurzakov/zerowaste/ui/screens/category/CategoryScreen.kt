package omurzakov.zerowaste.ui.screens.category

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import omurzakov.zerowaste.R
import omurzakov.zerowaste.navigation.INavigationRouter
import omurzakov.zerowaste.ui.theme.GreenyGreen
import omurzakov.zerowaste.utils.Constant.TT_CATEGORY_LIST
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterial3Api
@Composable
fun CategoryScreen(
    navigation: INavigationRouter,
    viewModel: CategoryViewModel = getViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.category)
                    )
                }
            )
        },
        content = {
            CategoryScreenDetail(
                paddingValues = it,
                navigation = navigation,
                viewModel = viewModel
            )
        },
    )
}

@Composable
fun CategoryScreenDetail(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    viewModel: CategoryViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .testTag(TT_CATEGORY_LIST),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.categories.forEach {
            item(key = it) {
                CategoryRow(
                    rowValue = it,
                    onRowClick = {
                        navigation.navigateToCategoryDetail(category = it)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryRow(
    rowValue: String,
    onRowClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 6.dp)
            .border(1.dp, GreenyGreen, shape = MaterialTheme.shapes.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
                .clickable(onClick = onRowClick)
        ) {
            Column {
                Text(
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(13.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = rowValue
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painterResource(R.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 2.dp)
            )
        }
    }
}