package com.example.smscypher;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.bean.ContactBean;
import com.example.listview.Adapter_contact;
import com.example.listview.Adapter_sms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SmsActivity extends Activity implements OnItemClickListener{
	
	private Context mContext;
	private ArrayList<String> smsList;
	private Adapter_sms adapter;
	private ListView lv_contact;
	
	 @Override
		protected void onCreate(Bundle savedInstanceState) {
			
	    	super.onCreate(savedInstanceState);
			setContentView(R.layout.activivty_contact);
			mContext = this;
			
			smsList = getSmsInPhone();
			lv_contact = (ListView) findViewById(R.id.lv_contact);
	    	lv_contact.setOnItemClickListener(this);
	    	
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (smsList.size()>0) {
						adapter = new Adapter_sms(mContext, smsList);
						lv_contact.setAdapter(adapter);
					} else {
						Log.e("这里没有被执行", "****");
					}
				}
			}).start();
			
		}
	 
	 public ArrayList<String> getSmsInPhone() {
			final String SMS_URI_ALL = "content://sms/";
			String str = "";
			final ArrayList<String> arrayList = new ArrayList<String>();
			try {
				Uri uri = Uri.parse(SMS_URI_ALL);
				String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
				Cursor cur = getContentResolver().query(uri, projection, null, null, "date desc");		// 获取手机内部短信

				if (cur.moveToFirst()) {
					int index_Address = cur.getColumnIndex("address");
					int index_Body = cur.getColumnIndex("body");
					int index_Date = cur.getColumnIndex("date");
					do {
						String strAddress = cur.getString(index_Address);
						String strbody = cur.getString(index_Body);
						long longDate = cur.getLong(index_Date);
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date d = new Date(longDate);
						String strDate = dateFormat.format(d);
						String strAddress2 = getPeopleNameFromPerson(strAddress);
						
						
						str = str + "电话：" + strAddress + "   " + "姓名：" + strAddress2 + "\n" +
						strbody + "\n" + "日期：" + strDate;
						
						arrayList.add(str.toString());
						
						str = "";
					} while (cur.moveToNext());

					if (!cur.isClosed()) {
						cur.close();
						cur = null;
					}
				} else {
					str +="no result!";
				}

			} catch (SQLiteException ex) {
				Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
			}
			return arrayList;
		}
		
		//通过address手机号关联Contacts联系人的显示名字
		private String getPeopleNameFromPerson(String address){
			if(address == null || address == ""){
				return "( no address )\n";
			}
			
			String strPerson = "null";
			String[] projection = new String[] {Phone.DISPLAY_NAME, Phone.NUMBER};
			
			Uri uri_Person = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, address);	// address 手机号过滤
			Cursor cursor = getContentResolver().query(uri_Person, projection, null, null, null);
			
			if(cursor.moveToFirst()){
				int index_PeopleName = cursor.getColumnIndex(Phone.DISPLAY_NAME);
				int index_PhoneNum = cursor.getColumnIndex(Phone.NUMBER);
				
				String strPeopleName = cursor.getString(index_PeopleName);
				String strPhoneNum = cursor.getString(index_PhoneNum);
				strPerson = "( " + strPeopleName + " : " + strPhoneNum + " )\n";
				strPerson = strPeopleName;
			}
			cursor.close();
			return strPerson;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			String string = smsList.get(position);
			String[] sourceStrArray = string.split("\n");
			
			Intent intent = new Intent();
		 	intent.putExtra("info", sourceStrArray[1]);
		 	
		 	//把数据返回给调用者
		 	setResult(10, intent);
		 	finish();
		}

}
