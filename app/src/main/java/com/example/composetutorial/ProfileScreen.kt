package com.example.composetutorial

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import java.io.File

@Composable
fun ProfileScreen(
    onNavigateToConversation: () -> Unit
) {

    val context = LocalContext.current
    val resolver = context.contentResolver
    val picture = File(context.filesDir, "picture")
    val name = File(context.filesDir, "name")

    var pictureUri by remember {
        mutableStateOf(picture.toUri().toString() + "?timestamp=${System.currentTimeMillis()}")
    }

    var text by remember {
        mutableStateOf(name.readBytes().decodeToString())
    }

    var pickedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        pickedImage = it

        if (it != null) {
            resolver.openInputStream(it).use { stream ->
                stream?.copyTo(picture.outputStream())
                stream?.close()
            }
        }
        pictureUri = picture.toUri().toString() + "?timestamp=${System.currentTimeMillis()}"
    }

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = MaterialTheme.colorScheme.secondary)
        )

        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = pictureUri,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    .clickable { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        TextField(
            value = text,
            onValueChange = { new ->
                text = new
                name.writeBytes(new.toByteArray())
            },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }

    Button(onClick = onNavigateToConversation, modifier = Modifier
        .height(40.dp)
        .padding(4.dp)) {
        Text(text = "Back")
    }
}
