package com.tnkfactory.pub.guide.feed_ad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.FeedAdView;
import com.tnkfactory.pub.guide.R;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        xmlFeedLoad();
        javaFeedLoad();
    }

    // 피드 광고 - XML 뷰 삽입 방식
    private void xmlFeedLoad() {
        FeedAdView feedAdView = findViewById(R.id.feed_ad_view);
        feedAdView.setListener(adListener);

        // 피드 광고 로드
        feedAdView.load();
    }

    // 피드 광고 - 뷰 동적 생성 방식
    private void javaFeedLoad() {
        RelativeLayout feedAdLayout = findViewById(R.id.feed_ad_layout);
        FeedAdView feedAdView = new FeedAdView(this, "TEST_FEED");
        feedAdLayout.addView(feedAdView);

        feedAdView.setListener(adListener);

        // 피드 광고 로드
        feedAdView.load();
    }

    private AdListener adListener = new AdListener() {
        /**
         * 광고 처리중 오류 발생시 호출됨
         * @param adItem 이벤트 대상이되는 AdItem 객체
         * @param error AdError
         */
        @Override
        public void onError(AdItem adItem, AdError error) {
            Toast.makeText(FeedActivity.this, "Feed Ad Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }

        /**
         * 광고 load() 후 광고가 도착하면 호출됨
         * @param adItem 이벤트 대상이되는 AdItem 객체
         */
        @Override
        public void onLoad(AdItem adItem) {
        }

        /**
         * 광고 화면이 화면이 나타나는 시점에 호출된다.
         * @param adItem 이벤트 대상이되는 AdItem 객체
         */
        @Override
        public void onShow(AdItem adItem) {
        }

        /**
         * 광고 클릭시 호출됨
         * 광고 화면은 닫히지 않음
         * @param adItem 이벤트 대상이되는 AdItem 객체
         */
        @Override
        public void onClick(AdItem adItem) {
        }
    };
}
