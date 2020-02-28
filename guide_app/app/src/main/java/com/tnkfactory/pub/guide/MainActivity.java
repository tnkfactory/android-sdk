package com.tnkfactory.pub.guide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tnkfactory.pub.guide.Interstitial_ad.InterstitialActivity;
import com.tnkfactory.pub.guide.Interstitial_ad.RewardVideoActivity;
import com.tnkfactory.pub.guide.banner_ad.BannerActivity;
import com.tnkfactory.pub.guide.feed_ad.FeedActivity;
import com.tnkfactory.pub.guide.feed_ad.FeedRecyclerViewActivity;
import com.tnkfactory.pub.guide.native_ad.NativeActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    // Sample layout
    private void initLayout() {
        ArrayList<MainListItem> itemList = new ArrayList();
        // Basic
        itemList.add(MainListItem.HEADER_01);
        itemList.add(MainListItem.BANNER);
        itemList.add(MainListItem.INTERSTITIAL);
        itemList.add(MainListItem.NATIVE);
        itemList.add(MainListItem.FEED);
        itemList.add(MainListItem.VIDEO_REWARD);

        // Custom
        itemList.add(MainListItem.HEADER_02);
        itemList.add(MainListItem.FEED_RECYCLERVIEW);

        ListView listView = findViewById(R.id.list_main);
        MainListAdapter adapter = new MainListAdapter(this, itemList);
        listView.setAdapter(adapter);

        // List Click Event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = null;

                MainListItem item = (MainListItem) view.getTag();
                switch (item) {
                    case BANNER:
                        intent = new Intent(getApplicationContext(), BannerActivity.class);
                        break;
                    case INTERSTITIAL:
                        intent = new Intent(getApplicationContext(), InterstitialActivity.class);
                        break;
                    case NATIVE:
                        intent = new Intent(getApplicationContext(), NativeActivity.class);
                        break;
                    case FEED:
                        intent = new Intent(getApplicationContext(), FeedActivity.class);
                        break;
                    case VIDEO_REWARD:
                        intent = new Intent(getApplicationContext(), RewardVideoActivity.class);
                        break;
                    case FEED_RECYCLERVIEW:
                        intent = new Intent(getApplicationContext(), FeedRecyclerViewActivity.class);
                        break;
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    // Sample list adapter
    public class MainListAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<MainListItem> list;

        public MainListAdapter(Context context, ArrayList<MainListItem> list) {
            this.context = context;
            this.list = list;
        }


        @Override
        public int getCount() {
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            MainListItem data = list.get(position);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(data.isHeader() == true ? R.layout.main_list_header : R.layout.main_list_item, null);
            }

            TextView txtItem = view.findViewById(R.id.text);
            txtItem.setText(data.getValue());
            view.setTag(data);

            return view;
        }
    }
}
