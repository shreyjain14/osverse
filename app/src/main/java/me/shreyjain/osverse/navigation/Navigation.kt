package me.shreyjain.osverse.navigation

sealed class Screen(val route: String) {
    object ModelSelection : Screen("model_selection")
    object ArViewer : Screen("ar_viewer")
}

data class ArModel(
    val id: Int,
    val name: String,
    val previewImage: Int? = null,
    val modelPath: String
) 