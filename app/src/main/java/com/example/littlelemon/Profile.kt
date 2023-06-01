package com.example.littlelemon


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("little_lemon", MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", "")
    val lastName = sharedPreferences.getString("lastName", "")
    val email = sharedPreferences.getString("email", "")
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logoImage",
            modifier = Modifier
                .size(200.dp, 90.dp)
        )
        Text(
            text = stringResource(R.string.profile_information),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp),
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Text(
            text = stringResource(R.string.first_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 2.dp),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF495E57)
            ),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium,
        )
        Box(
            modifier = Modifier
                .padding(16.dp, 2.dp, 16.dp, 32.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFF495E57),
                    shape = RoundedCornerShape(10.dp)
                )
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White)
        ) {
            Text(
                text = firstName ?: "",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF495E57)
                )
            )
        }
        Text(
            text = stringResource(R.string.last_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 2.dp),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF495E57)
            ),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium,
        )
        Box(
            modifier = Modifier
                .padding(16.dp, 2.dp, 16.dp, 32.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFF495E57),
                    shape = RoundedCornerShape(10.dp)
                )
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White)
        ) {
            Text(
                text = lastName ?: "",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF495E57)
                )
            )
        }
        Text(
            text = stringResource(R.string.email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 2.dp),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF495E57)
            ),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium,
        )
        Box(
            modifier = Modifier
                .padding(16.dp, 2.dp, 16.dp, 32.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFF495E57),
                    shape = RoundedCornerShape(10.dp)
                )
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White)
        ) {
            Text(
                text = email ?: "",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF495E57)
                )
            )
        }

        Spacer(Modifier.weight(1f)) // to move the button to the bottom of the screen
        Button(
            onClick = {
                val message = context.getString(R.string.logout_successful)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.navigate(
                    route = Onboarding.route
                ) {
                    // Pop up the back stack and exit the app
                    launchSingleTop = true
                    popUpTo(Onboarding.route) {
                        inclusive = true
                    }
                }
                clearUserData(context)
            },
            shape = RoundedCornerShape(30),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4CE14),
            )
        ) {
            Text(
                text = stringResource(R.string.logout),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun clearUserData(context: Context) {
    val sharedPreferences = context.getSharedPreferences("little_lemon", MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}