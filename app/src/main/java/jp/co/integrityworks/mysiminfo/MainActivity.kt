package jp.co.integrityworks.mysiminfo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.*
import jp.co.integrityworks.mysiminfo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val PERMISSIONS_REQUEST_CODE = 100
    private val REQUEST_MULTI_PERMISSIONS = 200
    private lateinit var binding: ActivityMainBinding
//    private lateinit var viewModel: TelViewModel
    //    private val mAd: RewardedVideoAd? = null
    private val viewModel: TelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.debug(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title =
                if (BuildConfig.DEBUG) getString(R.string.app_name) + " (deb)" else getString(R.string.app_name)

        checkMultiPermissions()

        binding.telViewModel = viewModel
        MobileAds.initialize(this) { }
        val adRequest: AdRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    // TODO パーミッション許可周りの実装を見直す
    private fun checkMultiPermissions() {
        val reqPermissions: ArrayList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
                reqPermissions.add(Manifest.permission.READ_PHONE_NUMBERS)
            }
        }
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            reqPermissions.add(Manifest.permission.READ_PHONE_STATE)
        }

        // 未許可
        if (reqPermissions.isNotEmpty()) {
            requestPermissions(
                    reqPermissions.toArray(arrayOfNulls(0)),
                    REQUEST_MULTI_PERMISSIONS)
        } else {
            viewModel.initParameters(applicationContext) // 初期表示時のデータ処理
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //許可された場合の処理
                    binding.telViewModel?.initParameters(applicationContext)
//                        if (permissions[0] == Manifest.permission.READ_PHONE_STATE) {
//
//                        }
//                    else {
//                            Logger.debug(TAG, "aaaaaaaaaaaaaa")
//                        }
                } else {
                    //拒否された場合の処理
                    Handler(Looper.getMainLooper()).post {
                        RuntimePermissionUtils().showAlertDialog(
                                supportFragmentManager,
                                "READ_PHONE_STATE"
                        )
                    }
                }
            }
            REQUEST_MULTI_PERMISSIONS -> {
                if (grantResults.isNotEmpty()) {
                    var activeNum = 0
                    for (i in permissions.indices) {
                        if (permissions[i] == Manifest.permission.READ_PHONE_NUMBERS) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Logger.debug(TAG, "READ_PHONE_NUMBERS: GRANTED")
                                activeNum++
                            } else {
                                // 拒否された時の対応
                                Handler(Looper.getMainLooper()).post {
                                    RuntimePermissionUtils().showAlertDialog(
                                            supportFragmentManager,
                                            "READ_PHONE_NUMBERS"
                                    )
                                }
                            }
                        } else if (permissions[i] == Manifest.permission.READ_PHONE_STATE) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                Logger.debug(TAG,"READ_PHONE_STATE: GRANTED")
                                activeNum++
                            } else {
                                // 拒否された時の対応
                                Handler(Looper.getMainLooper()).post {
                                    RuntimePermissionUtils().showAlertDialog(
                                            supportFragmentManager,
                                            "READ_PHONE_STATE"
                                    )
                                }
                            }
                        }
                    }
                    if (activeNum == 2) {
                        binding.telViewModel?.initParameters(applicationContext)
                    }
                }
            }
        }
    }
}