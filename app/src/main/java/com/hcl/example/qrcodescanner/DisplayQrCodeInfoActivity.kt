package com.hcl.example.qrcodescanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class DisplayQrCodeInfoActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var qrCodeDataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_qr_code_info)

        backButton = findViewById(R.id.backButton)
        qrCodeDataTextView = findViewById(R.id.qrCodeDataTextView)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Add this line to hide the default title

        val scannedData = intent.getStringExtra(EXTRA_QR_CODE_DATA)
        qrCodeDataTextView.text = scannedData

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_QR_CODE_DATA = "qr_code_data"
    }
}