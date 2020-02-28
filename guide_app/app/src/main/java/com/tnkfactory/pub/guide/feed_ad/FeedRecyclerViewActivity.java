package com.tnkfactory.pub.guide.feed_ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tnkfactory.ad.AdError;
import com.tnkfactory.ad.AdItem;
import com.tnkfactory.ad.AdListener;
import com.tnkfactory.ad.FeedAdView;
import com.tnkfactory.pub.guide.R;

import java.util.ArrayList;

public class FeedRecyclerViewActivity extends AppCompatActivity {

    public static int ITEM = 0;
    public static int AD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_recyclerview);

        // RecyclerView 셋팅
        RecyclerView recyclerView = findViewById(R.id.list_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FeedRecyclerViewAdapter(getListItem()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
    }

    // 리스트 더미 아이템 및 광고 위치 생성
    private ArrayList<FeedItem> getListItem() {
        ArrayList<FeedItem> items = new ArrayList();

        for (int i=0 ; i < 20; i++) {
            items.add(new FeedItem("Item " + i, ITEM));
        }

        items.add(15, new FeedItem("Feed AD", AD));

        return items;
    }

    // 리싸이클뷰 어댑터
    public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<FeedItem> items;

        public FeedRecyclerViewAdapter(ArrayList<FeedItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

            if (viewType == ITEM) {
                // 아이템 생성
                return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_list_item, parent, false));
            } else {
                // 피드 광고 생성
                FeedAdView feedAdView = new FeedAdView(parent.getContext(), "TEST_FEED");

                return new FeedAdViewHolder(feedAdView);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == ITEM) {
                ((ItemViewHolder) holder).textTitle.setText(items.get(position).getTitile());
            } else {
                // 피드 광고 로드
//                ((FeedAdViewHolder) holder).feedAdView.load();
            }

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            return items.get(position).getType();
        }

        // ViewHolder - Item
        class ItemViewHolder extends RecyclerView.ViewHolder {

            TextView textTitle;

            public ItemViewHolder(@NonNull View view) {
                super(view);
                this.textTitle = view.findViewById(R.id.feed_list_item_title);
            }
        }

        // ViewHolder - AD
        class FeedAdViewHolder extends RecyclerView.ViewHolder {

            FeedAdView feedAdView;

            public FeedAdViewHolder(@NonNull FeedAdView view) {
                super(view);

                feedAdView = view;
                feedAdView.setListener(new AdListener() {
                    /**
                     * 광고 처리중 오류 발생시 호출됨
                     * @param adItem 이벤트 대상이되는 AdItem 객체
                     * @param error AdError
                     */
                    @Override
                    public void onError(AdItem adItem, AdError error) {
                        Toast.makeText(FeedRecyclerViewActivity.this, "Feed Ad Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                });
                feedAdView.load();
            }
        }
    }

    // 리스트 아이템 클래스
    class FeedItem {
        final private String title;
        final private int type;

        public FeedItem(String title, int type) {
            this.title = title;
            this.type = type;
        }

        public String getTitile() {
            return title;
        }

        public int getType() {
            return type;
        }
    }

}
