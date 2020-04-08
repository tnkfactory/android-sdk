package com.tnkfactory.pub.sample.Interstitial_ad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.InterstitialAdItem;
import com.tnkfactory.pub.sample.R;

public class InterstitialActivity extends AppCompatActivity {

    private Button loadBtn;
    private Button showBtn;
    private InterstitialAdItem interstitialAdItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        // 전면 광고 로드 버튼
        loadBtn = findViewById(R.id.btn_interstitial_load);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInterstitialAd();
            }
        });

        // 전면 광고 노출 버튼
        showBtn = findViewById(R.id.btn_interstitial_show);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitialAd();
            }
        });
    }

    // 전면 광고 로드
    private void loadInterstitialAd() {
        interstitialAdItem = new InterstitialAdItem(this, "TEST_INTERSTITIAL_V");
        interstitialAdItem.setListener(new AdListener() {
            /**
             * 광고 처리중 오류 발생시 호출됨
             * @param adItem 이벤트 대상이되는 AdItem 객체
             * @param error AdError
             */
            @Override
            public void onError(AdItem adItem, AdError error) {
                Toast.makeText(InterstitialActivity.this, "Interstitial Ad Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            /**
             * 광고 load() 후 광고가 도착하면 호출됨
             * @param adItem 이벤트 대상이되는 AdItem 객체
             */
            @Override
            public void onLoad(AdItem adItem) {
                Toast.makeText(InterstitialActivity.this, "Interstitial Ad Load Complete", Toast.LENGTH_SHORT).show();
                showBtn.setVisibility(View.VISIBLE);
            }

            /**
             * 광고 화면이 화면이 나타나는 시점에 호출된다.
             * @param adItem 이벤트 대상이되는 AdItem 객체
             */
            @Override
            public void onShow(AdItem adItem) {
                showBtn.setVisibility(View.INVISIBLE);
            }

            /**
             * 광고 클릭시 호출됨
             * 광고 화면은 닫히지 않음
             * @param adItem 이벤트 대상이되는 AdItem 객체
             */
            @Override
            public void onClick(AdItem adItem) {
            }

            /**
             * 화면 닫힐 때 호출됨
             * @param adItem 이벤트 대상이되는 AdItem 객체
             * @param type 0:simple close, 1: auto close, 2:exit
             */
            @Override
            public void onClose(AdItem adItem, int type) {
            }
        });

        interstitialAdItem.load();
    }

    // 전면 광고 노출
    private void showInterstitialAd() {
        if (interstitialAdItem != null && interstitialAdItem.isLoaded()) {
            interstitialAdItem.show();
        }
    }
}
