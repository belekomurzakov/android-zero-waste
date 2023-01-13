package omurzakov.zerowaste.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import omurzakov.zerowaste.R
import omurzakov.zerowaste.navigation.INavigationRouter
import omurzakov.zerowaste.utils.Constant.TT_CHOOSE_CATEGORY_BUTTON
import omurzakov.zerowaste.utils.Constant.TT_DIALOG_WINDOW
import omurzakov.zerowaste.utils.Constant.TT_SCAN_PHOTO_BUTTON


@Composable
fun SortNowDialog(openDialog: MutableState<Boolean>, navigation: INavigationRouter) {

    val modifier = Modifier.padding(horizontal = 8.dp)

    AlertDialog(
        modifier = Modifier.testTag(TT_DIALOG_WINDOW),
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = { openDialog.value = false },
        title = { Text(text = stringResource(R.string.choose_way), fontWeight = FontWeight.Bold) },
        text = { Text(stringResource(R.string.dialog_description)) },
        buttons = {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth().testTag(TT_CHOOSE_CATEGORY_BUTTON),
                    onClick = {
                        navigation.navigateToListOfCategory()
                        openDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50)),
                ) {
                    Text(
                        stringResource(R.string.choose_category)
                    )
                }
            }

            Row(
                modifier = modifier.padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth().testTag(TT_SCAN_PHOTO_BUTTON),
                    onClick = {
                        navigation.navigateToPhoto()
                        openDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF03A9F4)),
                ) {
                    Text(
                        stringResource(R.string.scan_photo)
                    )
                }
            }
        }
    )
}