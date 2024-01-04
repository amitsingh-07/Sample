package com.hcl.example.qrcodescanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class ScanQrCodeActivity : AppCompatActivity() {
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var capture: CaptureManager
    private var isDecodingEnabled = true
    private val barcodeCallback = object : BarcodeCallback {

        override fun barcodeResult(result: BarcodeResult) {
            if (isDecodingEnabled) {
                result.text?.let { scannedData ->
                    Log.d(TAG, "Scanned QR code: $scannedData")
                    isDecodingEnabled = false
                    val intent =
                        Intent(this@ScanQrCodeActivity, DisplayQrCodeInfoActivity::class.java)
                    intent.putExtra(DisplayQrCodeInfoActivity.EXTRA_QR_CODE_DATA, scannedData)
                    startActivity(intent)
                }
            }
        }
        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scan)
        barcodeScannerView = findViewById(R.id.barcodeView)
        capture = CaptureManager(this, barcodeScannerView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }
    override fun onResume() {
        super.onResume()
        capture.onResume()
        barcodeScannerView.decodeContinuous(barcodeCallback)
        isDecodingEnabled = true
    }
    override fun onPause() {
        super.onPause()
        capture.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }
    companion object {
        private const val TAG = "ScanQrCodeActivity"
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}