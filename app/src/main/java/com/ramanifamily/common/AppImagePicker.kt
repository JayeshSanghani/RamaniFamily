package com.ramanifamily.common

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ramanifamily.R


@Composable
fun ProfileImagePicker(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // Camera launcher
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                cameraImageUri?.let { onImageSelected(it) }
            }
        }

    // Gallery launcher
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { onImageSelected(it) }
        }

    Box(
        modifier = modifier.size(140.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.green_700),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = imageUri?.let {
                    rememberAsyncImagePainter(it)
                } ?: painterResource(id = R.drawable.profilepic),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // PLUS ICON
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.green_700))
                .clickable { showSheet = true },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Image",
                tint = Color.White
            )
        }
    }

    // DIALOG (Camera / Gallery)
    if (showSheet) {
        ImagePickerBottomSheet(
            onCameraClick = {
                showSheet = false
                val uri = createImageUri(context)
                cameraImageUri = uri
                cameraLauncher.launch(uri)
            },
            onGalleryClick = {
                showSheet = false
                galleryLauncher.launch("image/*")
            },
            onDismiss = { showSheet = false }
        )

    }
}

@Composable
fun BottomSheetOption(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.green_700),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickerBottomSheet(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = colorResource(id = R.color.white),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp)
        ) {

            Text(
                text = stringResource(R.string.str_sel_image),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Divider()

            BottomSheetOption(
                icon = Icons.Default.CameraAlt,
                text = stringResource(R.string.str_camera),
                onClick = onCameraClick
            )

            Divider()

            BottomSheetOption(
                icon = Icons.Default.PhotoLibrary,
                text = stringResource(R.string.str_gallery),
                onClick = onGalleryClick
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun createImageUri(context: Context): Uri {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )!!
}

@Preview(showBackground = true)
@Composable
fun ProfileImagePickerPreview() {
    MaterialTheme {
        ProfileImagePicker(
            imageUri = null,
            onImageSelected = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ImagePickerBottomSheetPreview() {
    MaterialTheme {
        // Fake container for preview
        ImagePickerBottomSheet(
            onCameraClick = {},
            onGalleryClick = {},
            onDismiss = {}
        )
    }
}