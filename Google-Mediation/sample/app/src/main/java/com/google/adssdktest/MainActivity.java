package com.google.adssdktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.ads.mediationtestsuite.MediationTestSuite;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;

public class MainActivity extends AppCompatActivity {
    private String TAG = "TNK_AD";

    private InterstitialAd mInterstitialAd;
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 애드몹 초기화
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.i(TAG, initializationStatus.getAdapterStatusMap().toString());
            }
        });

        // 미디에이션 테스트 킷 버튼
        Button mediationBtn = findViewById(R.id.btn_MediationTestSuite);
        mediationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediationTestSuite.launch(MainActivity.this, "YOUR-APP-ID");
            }
        });
    }
}
