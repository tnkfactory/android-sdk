package com.google.adssdktest.mediation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;
import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.InterstitialAdItem;

public class TnkPubCustomInterstitial extends AdListener implements CustomEventInterstitial {

    private String TAG = "TNK_AD";
    private String placementId = "YOUR-PLACEMENT-ID-HERE";

    private InterstitialAdItem interstitialAdItem;
    private CustomEventInterstitialListener mInterstitialListener;

    private Context mContext = null;

    @Override
    public void requestInterstitialAd(Context context,
                                      CustomEventInterstitialListener listener,
                                      String serverParameter,
                                      MediationAdRequest mediationAdRequest,
                                      Bundle bundle) {

        // Tnk Pub 전면 광고 요청
        interstitialAdItem = new InterstitialAdItem(context, placementId, this);
        interstitialAdItem.load();

        mInterstitialListener = listener;
        mContext = context;
    }

    @Override
    public void showInterstitial() {
        // Tnk Pub 전면 광고 노출
        try {
            interstitialAdItem.show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad(AdItem adItem) {
        super.onLoad(adItem);
        Log.i(TAG, "Tnk Pub Interstitial Ad onLoad");
        mInterstitialListener.onAdLoaded();
    }

    @Override
    public void onShow(AdItem adItem) {
        super.onShow(adItem);
        Log.i(TAG, "Tnk Pub Interstitial Ad onShow");
        mInterstitialListener.onAdOpened();
    }

    @Override
    public void onClose(AdItem adItem, int type) {
        super.onClose(adItem, type);
        Log.i(TAG, "Tnk Pub Interstitial Ad onClose type : " + type);
        mInterstitialListener.onAdClosed();

        if (type == AdListener.CLOSE_EXIT) {
            // 앱 종료 기능 추가 지점

            if (mContext != null && mContext instanceof Activity) {
                ((Activity) mContext).finish();
            }
        }
    }

    @Override
    public void onClick(AdItem adItem) {
        super.onClick(adItem);
        Log.i(TAG, "Tnk Pub Interstitial Ad onClick");
        mInterstitialListener.onAdClicked();
        mInterstitialListener.onAdLeftApplication();
    }

    @Override
    public void onError(AdItem adItem, AdError error) {
        super.onError(adItem, error);
        Log.e(TAG,"Tnk Pub Interstitial Ad error code : " + error.getValue() + " / msg : " + error.getMessage());

        switch (error) {
            // BAD_REQUEST
            case FAIL_NO_PLACEMENT_ID:
            case FAIL_INCORRECT_PLACEMENT:
            case FAIL_SCREEN_ORIENTATION:
            case FAIL_INVALID_STATE:
            case FAIL_TEST_DEVICE_NOT_REGISTERED:
                mInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
                break;
            // NETWORK_ERROR
            case FAIL_TIMEOUT:
                mInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NETWORK_ERROR);
                break;
            // NO_INVENTORY
            case FAIL_NO_AD:
                mInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NO_FILL);
                break;
            // UNKNOWN
            case FAIL_SYSTEM:
                mInterstitialListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                break;
        }
    }

    @Override
    public void onResume() {
        Log.i(TAG, "Tnk Pub Interstitial Ad resume");
    }

    @Override
    public void onPause() {
        Log.i(TAG, "Tnk Pub Interstitial Ad pause");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Tnk Pub Interstitial Ad destory");
    }
}
