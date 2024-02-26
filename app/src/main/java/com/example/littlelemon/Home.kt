package com.example.littlelemon

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Enter
import androidx.compose.ui.input.key.Key.Companion.Tab
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.Karla_regular
import com.example.littlelemon.ui.theme.markazi_text_regular
import java.util.Locale


@Composable
fun HomeScreen(navController: NavHostController, menuItems: List<MenuItemRoom>) {

    // Define the selectedCategory variable in the outer scope
    var selectedCategory by remember { mutableStateOf("") }
    // Define the searchPhrase variable in the outer scope
    var searchPhrase by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Header(navController)

        // Hide the HeroSection in landscape mode to give more space to the menu
        if (LocalConfiguration.current.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            HeroSection(onSearchPhraseChanged = { searchPhrase = it })
        }

        ButtonRow(selectedCategory) { category ->
            selectedCategory = category
        }

        // menu items section with scrolling
        Column(Modifier.verticalScroll(rememberScrollState())) {
            val filteredItems =
                menuItems.filter { item ->
                    (item.category == selectedCategory.lowercase(Locale.getDefault()) || selectedCategory.isEmpty())
                            && item.title.contains(searchPhrase, ignoreCase = true)
                }

            if (filteredItems.isNotEmpty()) {
                Column(Modifier.padding(16.dp)) {
                    filteredItems.forEach { item ->
                        // Define the MenuItem Composable representing a single menu item.
                        MenuItem(item)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                if (searchPhrase.isNotBlank()) {
                    Text(
                        text = stringResource(R.string.no_matching),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF495E57)
                    )
                } else {
                    MenuItems(menuItems)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HeroSection(onSearchPhraseChanged: (String) -> Unit) {

    //// Access the current software keyboard controller
    val keyboardController = LocalSoftwareKeyboardController.current

    var searchPhrase by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(Color(0xFF495E57))
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .padding(start = 8.dp, top = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.restaurant_name),
                    style = TextStyle(
                        color = Color(0xFFF4CE14),
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                        fontFamily = markazi_text_regular
                    ),
                )
                Text(
                    text = stringResource(R.string.city),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        fontFamily = markazi_text_regular
                    ),
                )
                Text(
                    text = stringResource(R.string.description),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = Karla_regular
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.35f)
                    .padding(start = 8.dp, top = 8.dp)
                    .align(Alignment.CenterVertically),
            ) {
                Image(
                    painter = painterResource(R.drawable.hero_image),
                    contentDescription = "Hero image",
                    modifier = Modifier
                        .size(140.dp)
                        .padding(horizontal = 8.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = {
                searchPhrase = it
                onSearchPhraseChanged(it)
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    color = Color(0xFFEDEFEE)
                )
            },
            modifier = Modifier
                .padding(8.dp, 8.dp)
                .fillMaxWidth()
                .onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyUp && (event.key == Enter || event.key == Tab)) {
                        keyboardController?.hide() // Hide the keyboard when Enter or Tab key is pressed
                        true
                    } else {
                        false
                    }
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFF4CE14),
                unfocusedBorderColor = Color.White,
                textColor = Color(0xFFEDEFEE)
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color(0xFFEDEFEE)
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done), // Display "Done" button on the keyboard
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide() // Hide the keyboard when "Done" button is pressed
            })
        )


    }
}

@Composable
fun Header(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f)) // Add a spacer to push the logo image to the center
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logoImage",
            modifier = Modifier
                .size(150.dp, 60.dp)
        )
        Spacer(modifier = Modifier.weight(0.5f)) // Add a spacer to push the profile image to the right
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profileImage",
            modifier = Modifier
                .size(60.dp)
                .padding(end = 8.dp)
                .clickable {
                    navController.navigate(Profile.route)
                }
        )
    }
}

@Composable
fun MenuItems(menuItems: List<MenuItemRoom>) {
    // Use a Column layout to position items below each other.
    Column(Modifier.padding(16.dp)) {
        menuItems.forEach { item ->
            // Define the MenuItem Composable representing a single menu item.
            MenuItem(item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 0.5.dp,
                    color = Color(0xFF495E57)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.65f)
                .padding(start = 8.dp, top = 8.dp)
        ) {
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontFamily = Karla_regular
            )

            // Display only the first 80 letters of the description
            val displayDescription =
                if (expanded) item.description else item.description.take(80) + "..."

            Text(
                text = displayDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .clickable { expanded = !expanded },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Karla_regular,
                    color = Color(0xFF495E57)
                )
            )

            // Show "read more" if the description is longer than 80 characters
            if (item.description.length > 80) {
                Text(
                    text = if (expanded) "Read less" else "Read more",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                        .clickable { expanded = !expanded },
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF495E57)
                )
            }

            Text(
                text = "$${item.price}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontFamily = Karla_regular
            )
        }

        GlideImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}


@Composable
fun ButtonRow(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    // List of available categories
    val categories = listOf(
        stringResource(R.string.starters),
        stringResource(R.string.mains),
        stringResource(R.string.desserts)
    )

    // Row containing category buttons
    Row(modifier = Modifier.padding(8.dp)) {
        categories.forEach { category ->
            // Create a button for each category
            Button(
                onClick = {
                    // Toggle the category selection
                    val newSelectedCategory =
                        if (category.lowercase(Locale.getDefault()) == selectedCategory) {
                            // Unselect the category if it's already selected
                            ""
                        } else {
                            // Select the category if it's not selected
                            category.lowercase(Locale.getDefault())
                        }
                    // Notify the parent about the selected category
                    onCategorySelected(newSelectedCategory)
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category.lowercase(Locale.getDefault()) == selectedCategory) {
                        // Apply different background color for selected category
                        Color(0xFF495E57)
                    } else {
                        // Apply default background color for unselected categories
                        Color(0xFFEDEFEE)
                    }
                )
            ) {
                // Display the category name in the button
                Text(
                    text = category,
                    color = if (category.lowercase(Locale.getDefault()) == selectedCategory) {
                        // Apply different text color for selected category
                        Color(0xFFF4CE14)
                    } else {
                        // Apply default text color for unselected categories
                        Color.Black
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
