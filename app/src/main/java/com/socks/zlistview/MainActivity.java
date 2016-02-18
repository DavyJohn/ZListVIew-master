package com.socks.zlistview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.socks.zlistview.widget.ZListView;
import com.socks.zlistview.widget.ZListView.IXListViewListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

	protected static final String TAG = "MainActivity";
	private ZListViewAdapter adapter;
	private ZListView listView;
	private Handler handler = new Handler();
	private List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ZListView) findViewById(R.id.listview);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						listView.stopRefresh();
						onLoad();
					}
				}, 1000);
			}

			@Override
			public void onLoadMore() {

				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						listView.stopLoadMore();
						onLoad();
					}
				}, 1000);

			}
		});


		for (int i =0 ;i<20;i++){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("key", "你好" + i);
			list.add(map);

		}



		listView.setPullLoadEnable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				Toast.makeText(MainActivity.this, "onItemClick=" + position,
						Toast.LENGTH_SHORT).show();

			}

		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
										   int position, long id) {
				Toast.makeText(MainActivity.this,
						"onItemLongClick=" + position, Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});

		adapter=new ZListViewAdapter(MainActivity.this,list);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);


	}
	private void onLoad(){
		SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
		String time=dateFormat.format(new java.util.Date());
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(time);
	}

}
