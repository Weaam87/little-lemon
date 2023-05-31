package com.example.littlelemon

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                val message = context.getString(R.string.logout_successful)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.navigate(Onboarding.route)
                clearUserData(context)
            },
            shape = RoundedCornerShape(30),
            modifier = Modifier
                .padding(16.dp, 32.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4CE14)
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

fun clearUserData(context: Context) {
    val sharedPreferences = context.getSharedPreferences("little_lemon", MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}