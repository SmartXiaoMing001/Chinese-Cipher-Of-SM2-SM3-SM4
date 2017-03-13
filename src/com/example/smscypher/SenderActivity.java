package com.example.smscypher;

import java.util.ArrayList;
import com.example.method.SM4Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SenderActivity extends Activity {
	
	private static Context mContext;
	private EditText et_number;
	private EditText et_content;
	private EditText et_inputKey;
	private Integer flag = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sender);
		mContext = this;
		
		et_number = (EditText) findViewById(R.id.et_number); 
		et_content = (EditText) findViewById(R.id.et_content); 
		et_inputKey = (EditText) findViewById(R.id.et_inputKey); 
		
	}
	
	public void add(View v){
		Intent intent = new Intent(this,ContactsActivity.class);
		//startActivity(intent);
		startActivityForResult(intent, 1); //先写成1
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		//请求码和结果码，习惯哪个就用哪个就可以了，一个是第一界面的，一个是第二界面的
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 10) {
			String phone = data.getStringExtra("phone");
			et_number.setText(phone);
		} 
	}
	
	
	public void click(View v){
		String content = et_content.getText().toString().trim();
		String key = et_inputKey.getText().toString().trim();
		
		SM4Utils sm4Utils = new SM4Utils();
		if(pendingContent_edit(content) && pendingKey(key)){
			if (flag++ % 2 ==0) {
				et_content.setText(sm4Utils.getEncStr(content, key));
			} else {
				et_content.setText(sm4Utils.getDecStr(content, key));
			}
		}
	}
	
	public void send(View v){
		String number = et_number.getText().toString().trim();
		String content = et_content.getText().toString().trim();
		String key = et_inputKey.getText().toString().trim();
		
		if(pendingNumber(number) && pendingContent_send(content) && pendingKey(key)){
			SmsManager smsManager = SmsManager.getDefault();
			
			//这段代码是表示，如果短信字数过于长可以分段发送
			ArrayList<String> divideMessages = smsManager.divideMessage(content);
			for(String div : divideMessages) {
				smsManager.sendTextMessage(number, null, div,  null, null);
			}
			Toast.makeText(mContext, "发送成功", 0).show();
			this.finish();
		}
	}
	
	
	/**实现方法*/
	public Boolean pendingNumber(String number) {
		if(number.length() == 0) {
			Toast.makeText(mContext, "请输入联系人号码", 0).show();
			return false;
		}
		return true;
	}
	
	public Boolean pendingContent_edit(String content) {
		if(content.length() == 0) {
			Toast.makeText(mContext, "请编辑短信", 0).show();
			return false;
		}
		return true;
	}
	
	public Boolean pendingContent_send(String content) {
		if(content.length() == 0) {
			Toast.makeText(mContext, "请输入要发送的短信内容", 0).show();
			return false;
		}
		return true;
	}
	
	public Boolean pendingKey(String key) {
		if(key.length() == 0) {
			Toast.makeText(mContext, "请输入秘钥", 0).show();
			return false;
		}
		return true;
	}
}
