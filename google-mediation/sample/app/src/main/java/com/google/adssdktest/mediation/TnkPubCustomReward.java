package com.google.adssdktest.mediation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;
import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.InterstitialAdItem;

public class TnkPubCustomReward extends AdListener implements MediationRewardedVideoAdAdapter {

    private String TAG = "TNK_AD";
    private String placementId = "YOUR-PLACEMENT-ID-HERE";

    private InterstitialAdItem rewardAdItem;
    private MediationRewardedVideoAdListener mediationRewardedVideoAdListener;
    private boolean isInitialized;

    @Override
    public void initialize(Context context,
                           MediationAdRequest mediationAdRequest,
                           String unUsed,
                           MediationRewardedVideoAdListener listener,
                           Bundle serverParameters,
                           Bundle mediationExtras) {

        mediationRewardedVideoAdListener = listener;

        if (!(context instanceof Activity)) {
            isInitialized = false;
            mediationRewardedVideoAdListener.onInitializationFailed(this, AdRequest.ERROR_CODE_INVALID_REQUEST);
            return;
        }

        // Tnk Pub 리워드 광고 초기화
        rewardAdItem = new InterstitialAdItem(context, placementId, this);

        // 초기화 성공 리스너 호출
        isInitialized = true;
        mediationRewardedVideoAdListener.onInitializationSucceeded(this);
    }

    @Override
    public void loadAd(MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle1) {
        // Tnk Pub 리워드 광고 로드
        if (rewardAdItem != null) {
            rewardAdItem.load();
        }
    }

    @Override
    public void showVideo() {
        // Tnk Pub 리워드 광고 노출
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (rewardAdItem != null) {
                    rewardAdItem.show();
                }
            }
        });
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void onLoad(AdItem adItem) {
        super.onLoad(adItem);
        Log.i(TAG, "onLoad");
        mediationRewardedVideoAdListener.onAdLoaded(this);
    }

    @Override
    public void onShow(AdItem adItem) {
        super.onShow(adItem);
        Log.i(TAG, "onShow");
        mediationRewardedVideoAdListener.onAdOpened(this);
        mediationRewardedVideoAdListener.onVideoStarted(this);
    }

    @Override
    public void onClose(AdItem adItem, int type) {
        super.onClose(adItem, type);
        Log.i(TAG, "onClose");
        mediationRewardedVideoAdListener.onAdClosed(this);
    }

    @Override
    public void onClick(AdItem adItem) {
        super.onClick(adItem);
        Log.i(TAG, "onClick");
        mediationRewardedVideoAdListener.onAdClicked(this);
        mediationRewardedVideoAdListener.onAdLeftApplication(this);
    }

    @Override
    public void onVideoCompletion(AdItem adItem, int verifyCode) {
        super.onVideoCompletion(adItem, verifyCode);
        Log.i(TAG, "onVideoCompletion");

        // 리워드 적립 성공 시 리워드 타입과 포상금을 AdMob에 전달
        // 사용하려는 의도에 맞게 커스텀하여 사용
        if (verifyCode >= VIDEO_VERIFY_SUCCESS_SELF) {
            mediationRewardedVideoAdListener.onRewarded(this, new TnkPubCustomRewardItem("Reward", 1));
        }
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
                mediationRewardedVideoAdListener.onAdFailedToLoad(this, AdRequest.ERROR_CODE_INVALID_REQUEST);
                break;
            // NETWORK_ERROR
            case FAIL_TIMEOUT:
                mediationRewardedVideoAdListener.onAdFailedToLoad(this, AdRequest.ERROR_CODE_NETWORK_ERROR);
                break;
            // NO_INVENTORY
            case FAIL_NO_AD:
                mediationRewardedVideoAdListener.onAdFailedToLoad(this, AdRequest.ERROR_CODE_NO_FILL);
                break;
            // UNKNOWN
            case FAIL_SYSTEM:
                mediationRewardedVideoAdListener.onAdFailedToLoad(this, AdRequest.ERROR_CODE_INTERNAL_ERROR);
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
        Log.i(TAG, "destory");
    }
}
