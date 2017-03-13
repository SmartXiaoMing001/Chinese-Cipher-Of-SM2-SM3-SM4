package com.example.smscypher;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.bean.ContactBean;
import com.example.listview.Adapter_contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class ContactsActivity extends Activity implements OnItemClickListener{

    private Context mContext;
    private ListView lv_contact;
    private Adapter_contact adapter;
    private ArrayList<ContactBean> phoneContacts;

    /**获取库Phon表字段**/
    private static final String[] PHONES_PROJECTION = new String[] {
	    Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };
   
    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    
    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;
    
    /**头像ID**/
    private static final int PHONES_PHOTO_ID_INDEX = 2;
   
    /**联系人的ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activivty_contact);
		mContext = this;
		basicInfo();
		
	}
    
    private void basicInfo(){
    	
    	lv_contact = (ListView) findViewById(R.id.lv_contact);
    	lv_contact.setOnItemClickListener(this);
    	phoneContacts = getPhoneContacts();
    	
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (phoneContacts.size()>0) {
					adapter = new Adapter_contact(mContext, phoneContacts);
					lv_contact.setAdapter(adapter);
				} else {
					Log.e("这里没有被执行", "****");
				}
			}
		}).start();
	}
    
//    public void click(View v){
//    	for(int i=0;i<phoneContacts.size();i++){
//    		ContactBean bean = phoneContacts.get(i);
//    		System.out.println(bean.name);
//    	}
//    	adapter.notifyDataSetChanged();
//    }

	/** 得到手机通讯录联系人信息 **/
	private ArrayList<ContactBean> getPhoneContacts() {
		
	    ArrayList<ContactBean> arrayList = new ArrayList<ContactBean>();
	    ContentResolver resolver = mContext.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;

				// 得到联系人名称
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

				// 得到联系人ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

				// 得到联系人头像ID
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

				// 得到联系人头像Bitamp
				Bitmap contactPhoto = null;

				// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				if (photoid > 0) {
					Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				} else {
					// contactPhoto =
					// BitmapFactory.decodeResource(getResources(),
					// R.drawable.contact_photo);
				}
				System.out.println("contactName:" + contactName + "    " + "phoneNumber:" + phoneNumber
						+ "     contactPhoto:" + contactPhoto);
				
				ContactBean bean = new ContactBean();
				bean.name = contactName;
				bean.phone = phoneNumber;
				bean.photo = contactPhoto;
				arrayList.add(bean);
			}
			phoneCursor.close();
		}
		return arrayList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		ContactBean bean = phoneContacts.get(position);
		Intent intent = new Intent();
	 	intent.putExtra("phone", bean.phone);
	 	
	 	//把数据返回给调用者
	 	setResult(10, intent);
	 	finish();
	}
}
