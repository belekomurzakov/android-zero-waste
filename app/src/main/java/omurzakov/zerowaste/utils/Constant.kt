package omurzakov.zerowaste.utils

import omurzakov.zerowaste.models.Can
import omurzakov.zerowaste.models.UtilizedItem
import java.time.LocalDateTime
import java.time.ZoneOffset

object Constant {
    const val category = "category"
    const val mlModel = "mobilnet.tflite"
    const val id = "id"
    const val TEST_IMAGE =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/SMPTE_Color_Bars.svg/1200px-SMPTE_Color_Bars.svg.png"
    const val BASE_URL = "https://us-central1-zero-waste-5fe61.cloudfunctions.net/app/"

    const val TT_DIALOG_WINDOW = "dialog_window"

    const val TT_FLOAT_BUTTON = "floating_button"
    const val TT_SORT_NOW_BUTTON = "sort_button"
    const val TT_SCAN_PHOTO_BUTTON = "scan_photo_button"
    const val TT_CHOOSE_CATEGORY_BUTTON = "choose_category_button"

    const val TT_MAP_BOTTOM_ITEM = "map_bottom_item"
    const val TT_CATEGORY_BOTTOM_ITEM = "category_bottom_item"
    const val TT_HOME_BOTTOM_ITEM = "home_bottom_item"

    const val TT_CATEGORY_LIST = "category_list"
    const val TT_HISTORY_LIST = "history_list"

    const val TT_MAP_DETAIL_BUTTON = "map_detail_button"
    const val TT_SORT_IT_BUTTON = "sort_it_button"
    const val TT_SHEET_CARD = "sheet_card"
    const val TT_PICK_PHOTO = "pick_photo"
    const val TT_ANALYZE_BUTTON = "analyze_button"
    const val TT_DETECTED_OBJECTS = "detected_objects"
    const val TT_BACK_BUTTON = "back_button"

    const val TT_CONFIRM_BUTTON = "confirm_button"
    const val TT_CANCEL_BUTTON = "cancel_button"
    const val TT_INPUT_NUMBER = "input_number"

    val CANS_DIFF = listOf(
        Can(
            id = "1",
            latitude = 49.177776F,
            longitude = 16.62343F,
            tid = 1,
            typeWasteSeparated = "Clothes",
            commodityWasteSeparated = "Clothes",
            volume = "12",
            owner = "Brno-City",
            name = "Masaryk 12",
            street = "Masaryk",
            cp = 12,
            isPublic = "TRUE",
        ),
        Can(
            id = "2",
            latitude = 49.18134F,
            longitude = 16.683342F,
            tid = 2,
            typeWasteSeparated = "Paper",
            commodityWasteSeparated = "Paper",
            volume = "13",
            owner = "Brno-City",
            name = "Masaryk 13",
            street = "Masaryk",
            cp = 13,
            isPublic = "TRUE",
        )
    )

    val CANS_CLOTHES = listOf(
        Can(
            id = "1",
            latitude = 49.177776F,
            longitude = 16.62343F,
            tid = 1,
            typeWasteSeparated = "Clothes",
            commodityWasteSeparated = "Clothes",
            volume = "12",
            owner = "Brno-City",
            name = "Masaryk 12",
            street = "Masaryk",
            cp = 12,
            isPublic = "TRUE",
        ), Can(
            id = "2",
            latitude = 49.18134F,
            longitude = 16.683342F,
            tid = 2,
            typeWasteSeparated = "Clothes",
            commodityWasteSeparated = "Clothes",
            volume = "13",
            owner = "Brno-City",
            name = "Masaryk 13",
            street = "Masaryk",
            cp = 13,
            isPublic = "TRUE",
        )
    )

    val UT_ITEM = UtilizedItem(
        2,
        name = "Paper",
        image = TEST_IMAGE,
        number = 12,
        createdDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
    )
}