package dev.amal.protodatastore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import dev.amal.protodatastore.ui.theme.ProtoDataStoreTheme
import kotlinx.coroutines.launch

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProtoDataStoreTheme {
                val appSettings = dataStore.data.collectAsState(
                    initial = AppSettings()
                ).value
                val scope = rememberCoroutineScope()
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (i in 0..2) {
                        val language = Language.values()[i]
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = language == appSettings.language,
                                onClick = {
                                    scope.launch { setLanguage(language) }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = language.toString())
                        }
                    }
                }
            }
        }
    }

    private suspend fun setLanguage(language: Language) {
        dataStore.updateData { it.copy(language = language) }
    }
}
