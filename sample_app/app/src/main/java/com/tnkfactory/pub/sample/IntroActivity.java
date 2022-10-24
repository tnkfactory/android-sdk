package com.tnkfactory.pub.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.InterstitialAdItem;
import com.tnkfactory.pub.sample.Interstitial_ad.InterstitialActivity;
import com.tnkfactory.pub.sample.Interstitial_ad.RewardVideoActivity;
import com.tnkfactory.pub.sample.banner_ad.BannerActivity;
import com.tnkfactory.pub.sample.feed_ad.FeedActivity;
import com.tnkfactory.pub.sample.feed_ad.FeedRecyclerViewActivity;
import com.tnkfactory.pub.sample.native_ad.NativeActivity;
import com.tnkfactory.pub.sample.native_ad.NativeViewPagerActivity;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {

    private InterstitialAdItem startInterstitial = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        new Handler(Looper.getMainLooper()).postDelayed(this::showIntroInterstitial, 1000);

    }

    void moveToMain() {
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
    }

    void showIntroInterstitial() {
        InterstitialAdItem ad = new InterstitialAdItem(this, "TEST_INTERSTITIAL_V", new AdListener() {
            @Override
            public void onError(AdItem adItem, AdError error) {
                moveToMain();
                super.onError(adItem, error);
            }

            @Override
            public void onClose(AdItem adItem, int type) {
                moveToMain();
                super.onClose(adItem, type);
            }

            @Override
            public void onLoad(AdItem adItem) {
                adItem.show();
                super.onLoad(adItem);
            }
        });

        ad.load();
    }
}
