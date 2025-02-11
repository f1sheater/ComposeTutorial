package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val context = LocalContext.current
            //val resolver = context.contentResolver
            //val picture = File(context.filesDir, "picture")
            //val nameFile = File(context.filesDir, "name")
            ComposeTutorialTheme {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    NavHost()
                }
            }
        }
    }
}

@Serializable
object Conversation
@Serializable
object Profile

@Composable
fun NavHost(
    //picture: File,
    //name: File,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
    ) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Conversation
    ) {

        composable<Conversation> {
            ConversationScreen(
                onNavigateToProfile = {
                    navController.navigate(route = Profile) {
                        popUpTo(Profile) { inclusive = true }
                    }
                }
            )
        }

        composable<Profile> {
            ProfileScreen(
                onNavigateToConversation = {
                    navController.navigate(route = Conversation) {
                        popUpTo(Conversation) { inclusive = true }
                    }
                }
                //picture = picture,
                //name = name
            )
        }
    }
}
