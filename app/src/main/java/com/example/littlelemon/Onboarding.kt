package com.example.littlelemon

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavHostController) {

    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

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
            text = stringResource(R.string.welcome),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(vertical = 40.dp),
            fontSize = 26.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(R.string.information),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp),
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            value = firstName,
            onValueChange = { value -> firstName = value },
            label = { Text(text = stringResource(R.string.first_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFF4CE14),
                unfocusedBorderColor = Color(0xFF495E57),
                focusedLabelColor = Color(0xFF495E57),
                unfocusedLabelColor = Color(0xFF495E57),
                textColor = Color(0xFF495E57)
            )
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { value -> lastName = value },
            label = { Text(text = stringResource(R.string.last_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFF4CE14),
                unfocusedBorderColor = Color(0xFF495E57),
                focusedLabelColor = Color(0xFF495E57),
                unfocusedLabelColor = Color(0xFF495E57),
                textColor = Color(0xFF495E57)
            )
        )
        OutlinedTextField(
            value = email,
            onValueChange = { value -> email = value },
            label = { Text(text = stringResource(R.string.email)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFF4CE14),
                unfocusedBorderColor = Color(0xFF495E57),
                focusedLabelColor = Color(0xFF495E57),
                unfocusedLabelColor = Color(0xFF495E57),
                textColor = Color(0xFF495E57)
            )
        )
        Spacer(Modifier.weight(1f)) // to move the button to the bottom of the screen
        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    val message = context.getString(R.string.unsuccessful)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                } else {
                    val message = context.getString(R.string.successful)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    saveUserData(context, firstName, lastName, email)
                    navController.navigate(Home.route)
                }
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
                text = stringResource(R.string.register),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

fun saveUserData(context: Context, firstName: String, lastName: String, email: String) {
    val sharedPreferences = context.getSharedPreferences("little_lemon", MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("firstName", firstName)
    editor.putString("lastName", lastName)
    editor.putString("email", email)
    editor.apply()
}

fun userDataAvailable(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("little_lemon", MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", null)
    val lastName = sharedPreferences.getString("lastName", null)
    val email = sharedPreferences.getString("email", null)

    return firstName != null && lastName != null && email != null
}