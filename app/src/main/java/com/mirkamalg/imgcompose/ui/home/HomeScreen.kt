package com.mirkamalg.imgcompose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.mirkamalg.imgcompose.data.PhotosData
import com.mirkamalg.imgcompose.viewmodels.HomeViewModel

@Composable
fun Home() {

    val homeViewModel = viewModel(HomeViewModel::class.java)
    val homeState by homeViewModel.homeState.collectAsState()
    val baseState by homeViewModel.baseState.collectAsState()

    HomeContent(
        photos = homeState.data,
        isLoading = baseState.isLoading,
        onRefresh = homeViewModel::fetchData
    )
    ErrorDialog(
        errorMessage = baseState.errorMessage,
        homeViewModel::fetchData,
        homeViewModel::clearErrorState
    )
}

@Composable
fun HomeContent(
    photos: List<PhotosData>,
    isLoading: Boolean,
    onRefresh: () -> Unit
) {
    SwipeRefresh(state = SwipeRefreshState(isRefreshing = isLoading),
        onRefresh = {
            onRefresh()
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(photos) { photoData ->
                PhotoRow(data = photoData)
            }
        }
    }
}

@Composable
@Preview
fun PhotoRow(
    data: PhotosData = PhotosData(
        1,
        1,
        "Test",
        "https://via.placeholder.com/150/24f355",
        "https://via.placeholder.com/150/24f355"
    )
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberCoilPainter(
                request = data.url,
                fadeIn = true
            ),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Text(text = data.title.toString())
    }
}

@Composable
fun ErrorDialog(errorMessage: String?, onRetry: () -> Unit, onDismiss: () -> Unit) {

    var showErrorDialog by remember {
        mutableStateOf(errorMessage != null)
    }
    showErrorDialog = errorMessage != null

    if (showErrorDialog) {

        AlertDialog(
            text = {
                Text(text = errorMessage.toString())
            },
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRetry()
                    }
                ) {
                    Text(text = "Retry")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = "Dismiss")
                }
            }
        )
    }

}