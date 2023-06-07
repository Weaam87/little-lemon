package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header(navController)
        HeroSection()

        // Display menu items
        Column(Modifier.padding(16.dp)) {
            menuItemsState.forEach { menuItem ->
                MenuItem(item = menuItem)
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .background(Color(0xFF495E57))
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 16.dp, top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.restaurant_name),
                    style = TextStyle(
                        color = Color(0xFFF4CE14),
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        fontFamily = markazi_text_regular
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                )
                Text(
                    text = stringResource(R.string.city),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        fontFamily = markazi_text_regular
                    ),
                    modifier = Modifier.padding(start = 8.dp)
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
            Image(
                painter = painterResource(R.drawable.hero_image),
                contentDescription = "Hero image",
                modifier = Modifier
                    .weight(0.3f)
                    .size(120.dp)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = item.price,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            GlideImage(
                model = item.image, // Update this line
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
        }
    }
}
