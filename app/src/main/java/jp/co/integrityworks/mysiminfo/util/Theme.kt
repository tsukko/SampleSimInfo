package jp.co.integrityworks.mysiminfo.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ダークモード & ライトモードの色定義
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFF121212),
    onBackground = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EA),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFFFFFFFF),
    onBackground = Color.Black
)

// カスタムテーマ
@Composable
fun MyAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}
