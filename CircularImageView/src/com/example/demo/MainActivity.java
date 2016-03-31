package com.example.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.view.CircularImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listView);
		final ArrayList<String> dataList = new ArrayList<String>();
		for (int i = 0; i < 20; i++)
			dataList.add("item" + i);
		final ListBaseAdapter adapter = new ListBaseAdapter(this, dataList);
		listView.setAdapter(adapter);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				dataList.clear();
				for (int i = 20; i < 35; i++)
					dataList.add("item" + i);
				adapter.notifyDataSetChanged();
			}
		}, 5000);

	}

	private class ListBaseAdapter extends BaseAdapter {

		private LayoutInflater inflater = null;
		private ArrayList<String> dataList = null;

		public ListBaseAdapter(Context context, ArrayList<String> dataList) {
			inflater = LayoutInflater.from(context);
			this.dataList = dataList;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			CircularImageView imageView = null;
			TextView textView = null;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.listview_item, null);
				holder.imageView = (CircularImageView) convertView
						.findViewById(R.id.imageView);
				holder.textView = (TextView) convertView
						.findViewById(R.id.textView);
				convertView.setTag(holder);
			} else
				holder = (ViewHolder) convertView.getTag();

			String name = dataList.get(position);
			holder.textView.setText(name);

			if (position % 2 == 0)
				holder.imageView.setImageResource(R.drawable.meinv_0);
			else
				holder.imageView.setImageResource(R.drawable.meinv_1);

			return convertView;
		}

	}

}
