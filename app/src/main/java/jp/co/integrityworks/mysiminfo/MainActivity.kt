package jp.co.integrityworks.mysiminfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // AdMob初期化
//        MobileAds.initialize(this) { }
        setContent {
            NavigationSample()
        }
    }
}

