package com.example.gettingstartedwithjetpackcompose.features.allAccounts.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gettingstartedwithjetpackcompose.features.allAccounts.DashboardViewModel


@Composable
fun extractAge(age: String?): String {
    if (age.isNullOrEmpty()) {
        return "N/A"
    }
    val yearsMatch = Regex("(\\d+)y").find(age) // looks for digits followed by 'y'
    val monthsMatch = Regex("(\\d+)m").find(age) // looks for digits followed by 'm'

    val years = yearsMatch?.groupValues?.get(1)?.toIntOrNull() ?: 0 //if the years is null, return 0
    val months = monthsMatch?.groupValues?.get(1)?.toIntOrNull() ?: 0 //if the months is null, return 0

    return when {
        years > 0 -> "$years year${if (years > 1) "s" else ""}"
        months > 0 -> "$months month${if (months > 1) "s" else ""}"
        else -> "N/A"
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailsScreen(
    uuid: String,
    viewModel: DashboardViewModel,
    navController: NavController
) {
    // Trigger loading once when screen opens
    LaunchedEffect(uuid) {
        Log.d("DETAILS", "Calling loadAccountDetails for uuid = $uuid")
        viewModel.loadAccountDetails(uuid)
    }

    val account = viewModel.accountDetails.collectAsState()
    val isLoading = viewModel.detailsLoading.collectAsState()
    val error = viewModel.detailsError.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Account Details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFDE91EA),
                    titleContentColor = Color.Black,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { padding ->

        Surface(modifier = Modifier.fillMaxSize() .padding(padding), color = Color.White) {
            when {
                isLoading.value -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                error.value != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${error.value}")
                    }
                }

                account.value != null -> {
                    val acc = account.value!!

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        item {
                            Text("Basic Info", style = MaterialTheme.typography.titleLarge.copy(textDecoration = TextDecoration.Underline))
                            Spacer(Modifier.height(8.dp))
                            Text("Name: ${acc.accountsDto.names}")
                            Text("Gender: ${acc.accountsDto.gender}")
                            Text("Phone: ${acc.accountsDto.phone}")
                            Text("KYC Status: ${acc.accountsDto.kycStatus}")
                            Text("Created At: ${acc.accountsDto.createdAt}")
                            Text("Updated At: ${acc.accountsDto.updatedAt}")
                            Text("Notes: ${acc.accountsDto.notes ?: "None"}")
                            HorizontalDivider(Modifier.padding(vertical = 8.dp))
                        }

                        if (acc.wallets.isNotEmpty()) {
                            item {
                                Text("Wallets", style = MaterialTheme.typography.titleLarge.copy(textDecoration = TextDecoration.Underline))
                                Spacer(Modifier.height(8.dp))
                            }

                            acc.wallets.forEach { wallet ->
                                item {
                                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                        Text("- ${wallet.name} ", style = MaterialTheme.typography.titleMedium)
                                        Text("Balance: ${(wallet.currency)?.uppercase()} ${wallet.balance}")
                                        Text("Default Wallet: ${wallet.isDefault == 1}") //returns true if default wallet is 1, false otherwise
                                        HorizontalDivider(Modifier.padding(vertical = 8.dp))
                                    }
                                }
                            }
                            //item{ HorizontalDivider(Modifier.padding(vertical = 8.dp)) }
                        }

                        if (acc.dependants.isNotEmpty()) {
                            item {
                                Text("Dependants", style = MaterialTheme.typography.titleLarge.copy(textDecoration = TextDecoration.Underline))
                                Spacer(Modifier.height(8.dp))
                            }

                            acc.dependants.forEach { dependant ->
                                item {
                                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                        val ageText = extractAge(dependant.age)
                                        Text("- Name of dependant: ${dependant.names}")
                                        Text("  Age: $ageText.")

                                        if (dependant.iots.isNotEmpty()) {
                                            Text("  IOT Devices:")
                                            dependant.iots.forEach { iot ->
                                                Text("    - Device Serial Number: ${iot.serialNumber} ")
                                                Text("      Status: ${iot.status},")
                                                Text("      Assigned to: ${iot.assignee?.name}")
                                            }
                                        }

                                        HorizontalDivider(Modifier.padding(vertical = 8.dp))
                                    }
                                }
                            }
                            //item{ HorizontalDivider(Modifier.padding(vertical = 8.dp)) }
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No account data available.")
                    }
                }
            }
        }
    }
}