package com.google.adssdktest.mediation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;
import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.BannerAdView;

public class TnkPubCustomBanner extends AdListener implements CustomEventBanner {

    private String TAG = "TNK_AD";
    private String placementId = "YOUR-PLACEMENT-ID";

    private BannerAdView bannerAdView;
    private CustomEventBannerListener mBannerListener;

    @Override
    public void requestBannerAd(Context context,
                                CustomEventBannerListener listener,
                                String serverParameter,
                                AdSize adSize,
                                MediationAdRequest mediationAdRequest,
                                Bundle bundle) {

        // Tnk Pub 배너 광고 요청
        bannerAdView = new BannerAdView(context, placementId);
        bannerAdView.setListener(this);
        bannerAdView.load();

        mBannerListener = listener;
    }

    @Override
    public void onLoad(AdItem adItem) {
        super.onLoad(adItem);
        Log.i(TAG, "onLoad");
        mBannerListener.onAdLoaded(bannerAdView);
    }

    @Override
    public void onShow(AdItem adItem) {
        super.onShow(adItem);
        Log.i(TAG, "onShow");
    }

    @Override
    public void onClick(AdItem adItem) {
        super.onClick(adItem);
        Log.i(TAG, "onClick");
        mBannerListener.onAdClicked();
        mBannerListener.onAdOpened();
        mBannerListener.onAdLeftApplication();
    }

    @Override
    public void onError(AdItem adItem, AdError error) {
        super.onError(adItem, error);
        Log.e(TAG,"error code : " + error.getValue() + " / error msg : " + error.getMessage());

        switch (error) {
            // BAD_REQUEST
            case FAIL_NO_PLACEMENT_ID:
            case FAIL_INCORRECT_PLACEMENT:
            case FAIL_SCREEN_ORIENTATION:
            case FAIL_INVALID_STATE:
            case FAIL_TEST_DEVICE_NOT_REGISTERED:
                mBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
                break;
            // NETWORK_ERROR
            case FAIL_TIMEOUT:
                mBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NETWORK_ERROR);
                break;
            // NO_INVENTORY
            case FAIL_NO_AD:
                mBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                break;
            // UNKNOWN
            case FAIL_SYSTEM:
                mBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                break;
        }
    }

    @Override
    public void onResume() {
        Log.i(TAG, "resume");
    }

    @Override
    public void onPause() {
        Log.i(TAG, "pause");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "destroy");
    }

}
