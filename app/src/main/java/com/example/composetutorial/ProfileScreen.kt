package com.example.composetutorial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    onNavigateToConversation: () -> Unit
) {
    Column {
        Spacer(
            modifier = Modifier.fillMaxWidth().height(40.dp)
                .background(color = MaterialTheme.colorScheme.secondary)
        )

        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(R.drawable.test),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Lexi",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "User",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    Button(onClick = onNavigateToConversation, modifier = Modifier.height(40.dp).padding(4.dp)) {
        Text(text = "Back")
    }
}
