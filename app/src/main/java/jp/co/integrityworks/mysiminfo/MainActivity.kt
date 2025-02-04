package jp.co.integrityworks.mysiminfo

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.nativead.NativeAdView
import jp.co.integrityworks.mysiminfo.databinding.NativeAdLayoutBinding
import jp.co.integrityworks.mysiminfo.ui.theme.AdMobWithComposeSampleAppTheme

class MainActivity : ComponentActivity() {

    var nativeAdLayoutBinding: NativeAdLayoutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val banner = AdView(this)
        banner.setAdSize(AdSize.MEDIUM_RECTANGLE)
        banner.adUnitId = getString(R.string.ad_unit_id)
        banner.loadAd(AdRequest.Builder().build())

        // ネイティブ広告の初期化
        initNativeAd()

        setContent {
            CompositionLocalProvider(LocalActivity provides this) {
                // LocalActivityを取得できるようにする
                AdMobWithComposeSampleAppTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavigationHost(
                            banner = banner,
                            nativeAdView = nativeAdLayoutBinding!!.root as NativeAdView
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 広告の解放
        (nativeAdLayoutBinding?.root as? NativeAdView)?.destroy()
    }

    /**
     * ネイティブ広告の初期化
     */
    private fun initNativeAd() {
        nativeAdLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.native_ad_layout,
            null,
            false
        )
        AdLoader.Builder(this, getString(R.string.admob_native_unit_id))
            .forNativeAd { ad ->
                val nativeAdView = nativeAdLayoutBinding!!.root as NativeAdView

                nativeAdView.mediaView = nativeAdLayoutBinding!!.adMedia
                nativeAdLayoutBinding!!.adMedia.setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                nativeAdView.headlineView = nativeAdLayoutBinding!!.adHeadline
                nativeAdLayoutBinding!!.adHeadline.text = ad.headline
                nativeAdView.bodyView = nativeAdLayoutBinding!!.adBody
                nativeAdLayoutBinding!!.adBody.text = ad.body
                nativeAdView.adChoicesView = nativeAdLayoutBinding!!.adChoices
                nativeAdView.advertiserView = nativeAdLayoutBinding!!.adAdvertiser
                nativeAdLayoutBinding!!.adAdvertiser.text = ad.advertiser
                nativeAdView.setNativeAd(ad)
            }.build().loadAd(AdRequest.Builder().build())
    }
}

