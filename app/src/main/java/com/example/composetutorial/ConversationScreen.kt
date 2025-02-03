package com.example.composetutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConversationScreen(
    onNavigateToProfile: () -> Unit
) {
    Column {
        Spacer(
            modifier = Modifier.fillMaxWidth().height(40.dp)
                .background(color = MaterialTheme.colorScheme.secondary)
        )
        Conversation(SampleData.conversationSample)
    }

    Button(onClick = onNavigateToProfile, modifier = Modifier.height(40.dp).padding(4.dp)) {
        Text(text = "Profile")
    }
}
