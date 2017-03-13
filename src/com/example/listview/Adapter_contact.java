package com.example.listview;

import java.util.ArrayList;

import com.example.bean.ContactBean;
import com.example.smscypher.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_contact extends BaseAdapter{
	
	private ArrayList<ContactBean> list;
	private Context context;
	
	public Adapter_contact(Context context, ArrayList<ContactBean> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView != null) {
			view = convertView;
		} else {
			view = View.inflate(context, R.layout.item_contact_layout, null);
		}
		TextView item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
		TextView item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
		
		ContactBean bean = list.get(position);
		String name = "姓名：" + bean.name;
		String phone = "电话：" + bean.phone;
		
		item_tv_name.setText(name);
		item_tv_num.setText(phone);

		return view;
	}
	

}
