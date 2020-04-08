package com.tnkfactory.pub.sample.native_ad;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.NativeAdItem;
import com.tnkfactory.ad.NativeViewBinder;
import com.tnkfactory.pub.sample.R;

import java.util.ArrayList;

public class NativeViewPagerActivity extends AppCompatActivity {

    ViewPagerAdapter adAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_view_pager);

        ViewPager viewPager = findViewById(R.id.viewpager_native);
        adAdapter = new ViewPagerAdapter(getListItem());
        viewPager.setAdapter(adAdapter);

        loadNativeAd();
    }

    // 더미 아이템 및 광고 위치 생성
    private ArrayList<Object> getListItem() {
        ArrayList<Object> items = new ArrayList();

        for (int i = 1; i <= 4; i++) {
            items.add(i + " Page");
        }

        return items;
    }

    // 네이티브 광고 로드
    private void loadNativeAd() {
        NativeAdItem nativeAdItem = new NativeAdItem(this, "TEST_NATIVE");
        nativeAdItem.setListener(new AdListener() {
            /**
             * 광고 처리중 오류 발생시 호출됨
             * @param adItem 이벤트 대상이되는 AdItem 객체
             * @param error AdError
             */
            @Override
            public void onError(AdItem adItem, AdError error) {
                Toast.makeText(NativeViewPagerActivity.this, "Native Ad Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            /**
             * 광고 load() 후 광고가 도착하면 호출됨
             * @param adItem 이벤트 대상이되는 AdItem 객체
             */
            @Override
            public void onLoad(AdItem adItem) {
                Toast.makeText(NativeViewPagerActivity.this, "Native Ad Load Complete", Toast.LENGTH_SHORT).show();
                adAdapter.addNativeAdItem((NativeAdItem) adItem);
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
        });

        nativeAdItem.load();

    }

    public class ViewPagerAdapter extends PagerAdapter {

        ArrayList<Object> items;

        public ViewPagerAdapter(ArrayList<Object> items) {
            this.items = items;
        }

        public void addNativeAdItem(NativeAdItem nativeAdItem) {

            items.add(items.size()/2, nativeAdItem);

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewGroup layout;
            LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (items.get(position) instanceof NativeAdItem) {
                NativeAdItem nativeAdItem = (NativeAdItem) items.get(position);
                layout = (ViewGroup) inflater.inflate(R.layout.native_ad_item, container, false);
                container.addView(layout);

                // 네이티브 바인더 셋팅
                NativeViewBinder binder = new NativeViewBinder(R.id.native_ad_content);
                binder.iconId(R.id.native_ad_icon)
                        .titleId(R.id.native_ad_title)
                        .textId(R.id.native_ad_desc)
                        .starRatingId(R.id.native_ad_rating)
                        .watermarkIconId(R.id.native_ad_watermark_container)
                        .addClickView(R.id.native_ad_content);

                // 광고 노출
                nativeAdItem.attach(layout, binder);

            } else {
                layout = (ViewGroup) inflater.inflate(R.layout.view_pager_dummy_item, container, false);

                TextView textView = layout.findViewById(R.id.pager_dummy_text);
                textView.setText((String) items.get(position));
                container.addView(layout);
            }

            return layout;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
