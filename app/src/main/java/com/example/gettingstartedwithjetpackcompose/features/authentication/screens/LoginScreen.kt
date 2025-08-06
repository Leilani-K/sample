package com.example.gettingstartedwithjetpackcompose.features.authentication.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gettingstartedwithjetpackcompose.R
import com.example.gettingstartedwithjetpackcompose.features.authentication.viewModel.AuthViewModel
import com.example.gettingstartedwithjetpackcompose.ui.theme.Roboto


@Composable
fun LoginScreen(onNavigateToLanding: () -> Unit, viewModel: AuthViewModel = hiltViewModel(),
                onNavigateToRegister: () -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }
    val state by viewModel.loginState.collectAsState()


    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            viewModel.login()
            onNavigateToLanding() //takes us to home screen
            viewModel.clearLoginSuccess()  // resets isLoggedIn flag so we don’t loop
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, height * 0.6f)
                cubicTo(
                    width * 0.2f,
                    height * 0.35f,
                    width * 0.7f,
                    height * 0.65f,
                    width,
                    height * 0.6f
                )
                lineTo(width, 0f)
                close()
            }
            drawPath(path, color = Color(0xFFDE91EA))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                shape = RoundedCornerShape(24.dp), modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 120.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {

                Column(
                    modifier = Modifier.padding(
                        top = 50.dp,
                        bottom = 20.dp,
                        start = 10.dp,
                        end = 10.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("LOG IN", fontSize = 50.sp, fontFamily = Roboto)
                    //Text("to start your journey", fontFamily = Roboto, fontSize = 30.sp)
                    Spacer(modifier = Modifier.height(20.dp))

//                    OutlinedTextField(
//                        value = state.username,
//                        onValueChange = viewModel::onLoginUsernameChange,
//                        label = { Text("Username") },
//                        singleLine = true,
//                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
//                        leadingIcon = {
//                            Icon(
//                                imageVector = Icons.Filled.Person,
//                                contentDescription = "Username icon"
//                            )
//                        }
//                    )

                    Spacer(modifier = Modifier.height(25.dp))
//
//
//                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = viewModel::onLoginEmailChange,
                        label = { Text("Email") },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.MailOutline,
                                contentDescription = "Email icon"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = viewModel::onLoginPasswordChange,
                        label = { Text("Password") },
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Password icon"
                            )
                        },
                        trailingIcon = {
                            var visibilityIcon =
                                if (showPassword) R.drawable.visibility_on else R.drawable.visibility_off
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    painter = painterResource(visibilityIcon),
                                    contentDescription = if (showPassword) "show password" else "hide password",
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                        }
                    )

                    state.error?.let { errorText ->
                        Text(
                            text = errorText,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    FilledTonalButton(
                        modifier = Modifier.width(280.dp),
                        onClick = viewModel::login,
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) CircularProgressIndicator()
                        else Text("Log In",fontFamily = Roboto, fontSize = 15.sp)
                    }

                }

            }
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don't have an account?", fontFamily = Roboto, fontSize = 10.sp)
                TextButton(onClick = onNavigateToRegister) {
                    Text(
                        "Register now",
                        fontFamily = Roboto,
                        fontSize = 10.sp
                    )
                }
            }
        }

    }
}