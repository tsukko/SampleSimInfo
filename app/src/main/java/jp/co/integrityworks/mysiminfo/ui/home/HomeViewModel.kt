package jp.co.integrityworks.mysiminfo.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.telephony.SubscriptionManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var line1Number by mutableStateOf("")
        private set
    var simCountryIso by mutableStateOf("")
        private set
    var simSerialNumber by mutableStateOf("")
        private set
    var deviceId by mutableStateOf("")
        private set
    var androidId by mutableStateOf("")
        private set
    var simOperator by mutableStateOf("")
        private set
    var simOperatorName by mutableStateOf("")
        private set
    var simState by mutableStateOf("")
        private set
    var voiceMailNumber by mutableStateOf("")
        private set

    @SuppressLint("MissingPermission", "HardwareIds")
    fun initParameters(context: Context) {
        // TelephonyManager, SubscriptionManager の取得
        val telMgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val subscriptionManager =
            context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

        // アクティブなサブスクリプション情報の処理（必要に応じて利用）
        for (subscriptionInfo in subscriptionManager.activeSubscriptionInfoList!!) {
            val subscriptionId =
                telephonyManager.createForSubscriptionId(subscriptionInfo.subscriptionId)
            // 必要であればsubscriptionIdを利用した処理を記述
        }
        val hasCarrierPrivileges = telMgr.hasCarrierPrivileges()
        // 各パラメータの取得
        line1Number = telMgr.line1Number ?: ""
        simCountryIso = telMgr.simCountryIso ?: ""
        simSerialNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            "Not supported on iOS 10 or higher"
        } else {
            telMgr.simSerialNumber ?: ""
        }
        deviceId = "Not supported on iOS 10 or higher"
        androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        simOperator = telMgr.simOperator ?: ""
        simOperatorName = telMgr.simOperatorName ?: ""
        simState = telMgr.simState.toString()
        voiceMailNumber = telMgr.voiceMailNumber ?: ""
    }
}
