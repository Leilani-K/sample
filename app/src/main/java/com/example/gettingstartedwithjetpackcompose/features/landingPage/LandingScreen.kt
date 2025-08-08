package com.example.gettingstartedwithjetpackcompose.features.landingPage

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import com.example.gettingstartedwithjetpackcompose.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gettingstartedwithjetpackcompose.features.navigation.Routes
import com.example.gettingstartedwithjetpackcompose.ota.OtaViewModel
import com.example.gettingstartedwithjetpackcompose.BuildConfig


@Composable
fun CardTemplate( icon: Painter, title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Make it square and responsive
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = Color(0xFF90599D)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class NavCardItem(
    val title: String,
    val icon: Painter,
    val onClick: () -> Unit
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    navController: NavController,
    username: String,
    onNotesHomeClick: () -> Unit,
    onAccountsDashboardClick: () -> Unit,
    otaViewModel: OtaViewModel = hiltViewModel()
) {
    val versionInfo by otaViewModel.versionInfo.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        otaViewModel.checkForUpdate()
    }

    versionInfo?.let { info ->
        if (info.latestVersionCode > BuildConfig.VERSION_CODE) {
            AlertDialog(
                onDismissRequest = { /* Optional */ },
                title = { Text("Update Available") },
                text = {
                    Text("Version ${info.latestVersionName} is available.\n\n${info.releaseNotes}")
                },
                confirmButton = {
                    TextButton(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(info.apkUrl))
                        context.startActivity(intent)
                    }) {
                        Text("Update")
                    }
                },
//                dismissButton = {
//                    TextButton(onClick = { showDialog }) {
//                        Text("Later")
//                    }
//                }
            )
        }
    }




    val navItems = listOf(
        NavCardItem("Notes", painterResource(id = R.drawable.ic_dashboard_notes), onNotesHomeClick),
        NavCardItem("All Accounts", painterResource(id = R.drawable.ic_all_accounts), onAccountsDashboardClick)
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome, $username!",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFDE91EA),
                    titleContentColor = Color.Black,
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.MY_ACCOUNT) }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "My Account",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(24.dp)
        ) {
            Text(
                text = "Choose an action below:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(navItems) { item ->
                    CardTemplate(
                        icon = item.icon,
                        title = item.title,
                        onClick = item.onClick
                    )
                }
            }
        }
    }
}