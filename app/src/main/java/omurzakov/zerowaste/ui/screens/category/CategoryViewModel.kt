package omurzakov.zerowaste.ui.screens.category

import omurzakov.zerowaste.architecture.BaseViewModel

class CategoryViewModel : BaseViewModel() {

    val categories = mutableListOf(
        "Paper",
        "Biological waste",
        "Clothes",
        "White glass",
        "Stained glass",
        "Plastic",
        "Beverage cartons",
        "Cans"
    )
}