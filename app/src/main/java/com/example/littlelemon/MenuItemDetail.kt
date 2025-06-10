package com.example.littlelemon

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.Karla_regular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemDetailScreen(navController: NavHostController, itemId: Int) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = remember { db.menuItemDao() }

    var menuItem by remember { mutableStateOf<MenuItemRoom?>(null) }
    var instructions by remember { mutableStateOf("") }

    LaunchedEffect(itemId) {
        menuItem = withContext(Dispatchers.IO) { dao.getById(itemId) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color(0xFF495E57)
                )
            }
            BadgedBox(badge = {
                val count = CartRepository.getItemCount()
                if (count > 0) Badge { Text(count.toString()) }
            }) {
                IconButton(onClick = { navController.navigate(OrderSummary.route) }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = stringResource(id = R.string.cart),
                        tint = Color(0xFF495E57)
                    )
                }
            }
        }

        menuItem?.let { item ->
            GlideImage(
                model = item.image,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.title,
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = Karla_regular
            )

            Text(
                text = item.description,
                modifier = Modifier.padding(top = 8.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF495E57),
                    fontFamily = Karla_regular
                )
            )

            Text(
                text = "$${item.price}",
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = Karla_regular
            )

            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text(text = stringResource(R.string.special_instructions)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF4CE14),
                    unfocusedBorderColor = Color(0xFF495E57),
                    focusedLabelColor = Color(0xFF495E57),
                    unfocusedLabelColor = Color(0xFF495E57),
                    focusedTextColor = Color(0xFF495E57),
                    unfocusedTextColor = Color(0xFF495E57)
                )
            )

            Button(
                onClick = {
                    CartRepository.addItem(item, instructions)
                    Toast.makeText(context, context.getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
            ) {
                Text(
                    text = stringResource(R.string.add_to_cart),
                    color = Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
            }
        }
    }
}

