package com.example.kazuki.fingerprintauth;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fingerAuth();
    }

    private void fingerAuth() {
        final TextView textView;
        textView = (TextView) findViewById(R.id.fingerprint_status);
        FingerprintManager fingerprintManager = (FingerprintManager)
                getSystemService(FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (fingerprintManager.isHardwareDetected() || fingerprintManager.hasEnrolledFingerprints()) {
            fingerprintManager.authenticate(null, null, 0, new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    textView.setText(errString + "(error code:" + errorCode + ")");
                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                    textView.setText(helpString + "(help code:" + helpCode + ")");
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    textView.setText("認識しました．");
                }

                @Override
                public void onAuthenticationFailed() {
                    textView.setText("認識できませんでした．");
                }
            }, new Handler());
        }
    }
}
