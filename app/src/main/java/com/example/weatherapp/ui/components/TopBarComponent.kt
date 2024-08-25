import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.utils.hexToColorInt
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    text: MutableLiveData<String>,
    onLanguageChanged: () -> Unit = {}
) {
    val context = LocalContext.current
    var timerValue by remember { mutableIntStateOf(10) }
    var timerRunning by remember { mutableStateOf(true) }

    LaunchedEffect(timerRunning) {
        while (timerRunning && timerValue > 0) {
            delay(1000)
            timerValue--
            if (timerValue == 0) {
                timerValue = 10
                timerRunning = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {},
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(
                            onClick = {
                                val currentLanguage = text.value ?: return@Button
                                text.value = if (currentLanguage == "es") "en" else "es"
                                onLanguageChanged()
                                Toast.makeText(
                                    context,
                                    "${text.value?.uppercase()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(hexToColorInt("#1F316F"))
                        ) {
                            Icon(imageVector = Icons.Filled.Translate, contentDescription = "Language")
                        }
                        Text(
                            text = String.format("%02d:%02d", timerValue / 60, timerValue % 60),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = hexToColorInt("#1A4870")
                        )
                    }
                }
            )
        }
    ) {  innerPadding ->

    }
}