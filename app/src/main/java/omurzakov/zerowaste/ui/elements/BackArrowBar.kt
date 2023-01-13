package omurzakov.zerowaste.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import omurzakov.zerowaste.R
import omurzakov.zerowaste.utils.Constant.TT_BACK_BUTTON

@Composable
fun BackArrowBar(
    topBarText: String, actions: @Composable RowScope.() -> Unit = {}, onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = topBarText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .weight(1.5f)
                )
            }
        },
        navigationIcon = {
            androidx.compose.material3.IconButton(
                onClick = onBackClick,
                modifier = Modifier.testTag(TT_BACK_BUTTON)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.Black
                )
            }
        },
        backgroundColor = androidx.compose.material.MaterialTheme.colors.background,
        actions = actions
    )
}