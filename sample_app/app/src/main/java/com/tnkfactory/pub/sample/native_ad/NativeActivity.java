package com.tnkfactory.pub.sample.native_ad;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.NativeAdItem;
import com.tnkfactory.ad.NativeViewBinder;
import com.tnkfactory.pub.sample.R;

public class NativeActivity extends AppCompatActivity {

    private Button loadBtn;
    private Button showBtn;
    private NativeAdItem nativeAdItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        // 네이티브 광고 로드 버튼
        loadBtn = findViewById(R.id.btn_native_load);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNativeAd();
            }
        });

        // 네이티브 광고 노출 버튼
        showBtn = findViewById(R.id.btn_native_show);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNativeAd();
            }
        });
    }

    // 네이티브 광고 로드
    private void loadNativeAd() {
        nativeAdItem = new NativeAdItem(this, "TEST_NATIVE");
        nativeAdItem.setListener(new AdListener() {
            /**
             * 광고 처리중 오류 발생시 호출됨
             * @param adItem 이벤트 대상이되는 AdItem 객체
             * @param error AdError
             */
            @Override
            public void onError(AdItem adItem, AdError error) {
                Toast.makeText(NativeActivity.this, "Native Ad Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            /**
             * 광고 load() 후 광고가 도착하면 호출됨
             * @param adItem 이벤트 대상이되는 AdItem 객체
             */
            @Override
            public void onLoad(AdItem adItem) {
                Toast.makeText(NativeActivity.this, "Native Ad Load Complete", Toast.LENGTH_SHORT).show();
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
        });

        // 네이티브 광고 로드
        nativeAdItem.load();
    }

    // 네이티브 광고 노출
    private void showNativeAd() {

        if (nativeAdItem != null & nativeAdItem.isLoaded()) {

            // 네이티브 광고가 삽입될 컨테이너 초기화
            ViewGroup adContainer = findViewById(R.id.native_ad_container);
            adContainer.removeAllViews();

            // 네이티브 아이템 레이아웃 삽입
            ViewGroup view = (ViewGroup) View.inflate(this, R.layout.native_ad_item, adContainer);

            // 네이티브 바인더 객체 생성
            // 생성자에 메인 컨텐츠가 표시될 뷰 ID 필수 입력
            NativeViewBinder binder = new NativeViewBinder(R.id.native_ad_content);

            // 네이티브 바인더 셋팅
            binder.iconId(R.id.native_ad_icon)
                    .titleId(R.id.native_ad_title)
                    .textId(R.id.native_ad_desc)
                    .starRatingId(R.id.native_ad_rating)
                    .watermarkIconId(R.id.native_ad_watermark_container)
                    .addClickView(R.id.native_ad_content);

            // 네이티브 광고 노출
            nativeAdItem.attach(view, binder);
        }
    }
}
