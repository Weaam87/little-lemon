package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding() {
    Column(
        Modifier
            .fillMaxWidth()
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
            value = "",
            onValueChange = { },
            label = { Text(text = stringResource(R.string.first_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFF4CE14),
                unfocusedBorderColor = Color(0xFF495E57),
                focusedLabelColor = Color(0xFFF4CE14),
                unfocusedLabelColor = Color(0xFF495E57)
            )
        )
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text(text = stringResource(R.string.last_name)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFF4CE14),
                unfocusedBorderColor = Color(0xFF495E57),
                focusedLabelColor = Color(0xFFF4CE14),
                unfocusedLabelColor = Color(0xFF495E57)
            )
        )
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text(text = stringResource(R.string.email)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFF4CE14),
                unfocusedBorderColor = Color(0xFF495E57),
                focusedLabelColor = Color(0xFFF4CE14),
                unfocusedLabelColor = Color(0xFF495E57)
            )
        )
        Button(
            onClick = {},
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


@Preview
@Composable
fun OnboardingPreview() {
    Onboarding()
}
