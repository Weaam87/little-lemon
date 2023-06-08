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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.Karla_regular
import com.example.littlelemon.ui.theme.markazi_text_regular

@Composable
fun HomeScreen(navController: NavHostController, menuItems: List<MenuItemRoom>) {

    // Create a mutable state list to hold the menu items with Compose-aware behavior
    val menuItemsState = remember { mutableStateListOf<MenuItemRoom>() }

    // Add all the items from the provided menuItems list to the mutable state list
    menuItemsState.addAll(menuItems)

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Header(navController)
        // Hide the HeroSection in landscape mode to give more space to the menu
        if (LocalConfiguration.current.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            HeroSection()
        }

        // menu items section with scrolling
        Column(Modifier.verticalScroll(rememberScrollState())) {
            MenuItems(menuItems)
        }
    }

}

@Composable
fun HeroSection() {
    Row(
        modifier = Modifier
            .background(Color(0xFF495E57))
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.65f)
                .padding(start = 8.dp, top = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.restaurant_name),
                style = TextStyle(
                    color = Color(0xFFF4CE14),
                    fontWeight = FontWeight.Bold,
                    fontSize = 52.sp,
                    fontFamily = markazi_text_regular
                ),
            )
            Text(
                text = stringResource(R.string.city),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 38.sp,
                    fontFamily = markazi_text_regular
                ),
            )
            Text(
                text = stringResource(R.string.description),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = Karla_regular
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(0.35f)
                .padding(start = 16.dp, top = 8.dp)
                .align(Alignment.CenterVertically),
        ) {
            Image(
                painter = painterResource(R.drawable.hero_image),
                contentDescription = "Hero image",
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}


@Preview
@Composable
fun HeroSectionPreview() {
    HeroSection()
}

@Composable
fun Header(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f)) // Add a spacer to push the logo image to the center
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logoImage",
            modifier = Modifier
                .size(200.dp, 90.dp)
        )
        Spacer(modifier = Modifier.weight(0.5f)) // Add a spacer to push the profile image to the right
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profileImage",
            modifier = Modifier
                .size(80.dp)
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
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = Color(0xFF495E57)
                ),
                shape = RoundedCornerShape(10.dp)
            )
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
                    .padding(8.dp),
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontFamily = Karla_regular
            )
            Text(
                text = item.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color(0xFF495E57)
                )
            )
            Text(
                text = "$${item.price}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontFamily = Karla_regular
            )
        }
        Column(
            modifier = Modifier
                .weight(0.35f)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            //Use the GlideImage library to load images using the URL present in the menu item image attribute.
            GlideImage(
                model = item.image,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
                    .fillMaxSize()
                    .aspectRatio(1f) // Maintain the aspect ratio of the image,
                    .clip(shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )
        }
    }
}