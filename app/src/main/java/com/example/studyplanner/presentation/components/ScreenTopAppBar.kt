package com.example.studyplanner.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.WindowInsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopAppBar(
	title: String,
	onBackClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	CenterAlignedTopAppBar(
		title = { Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
		navigationIcon = {
			IconButton(onClick = onBackClick) {
				Icon(Icons.Default.ArrowBack, "Volver")
			}
		},
		modifier = modifier,
		windowInsets = WindowInsets(top = 0.dp)
	)
}
