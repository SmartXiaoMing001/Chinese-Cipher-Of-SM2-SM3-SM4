package com.example.smscypher;

import com.example.method.SM4Utils;
import com.example.ways.OperationUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ReceiverActivity extends Activity {

	private Context mContext;
	private EditText et_key_rec;
	private EditText et_content_rec;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receiver);
		mContext = this;
		
		et_key_rec = (EditText) findViewById(R.id.et_key_rec);
		et_content_rec = (EditText) findViewById(R.id.et_content_rec);
	}
	
	public void click1(View v){
		Intent intent = new Intent(this,SmsActivity.class);
		//startActivity(intent);
		startActivityForResult(intent, 1); //先写成1
	}
	
	public void click2(View v) {
		String key = et_key_rec.getText().toString().trim();
		String content = et_content_rec.getText().toString().trim();
		SM4Utils sm4Utils = new SM4Utils();
		if(pendingKey(key) && pendingContent(content)) {
			try {
				et_content_rec.setText(sm4Utils.getDecStr(content, key));
			} catch (Exception e) {
				Log.e("这里解不开", "");
			}
		}
	}
	
	/**实现方法*/
	public Boolean pendingKey(String key) {
		if(key.length() == 0) {
			Toast.makeText(mContext, "请输入秘钥", 0).show();
			return false;
		}
		return true;
	}
	
	public Boolean pendingContent(String content) {
		if(content.length() == 0) {
			Toast.makeText(mContext, "请输入要解密的短信", 0).show();
			return false;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		//请求码和结果码，习惯哪个就用哪个就可以了，一个是第一界面的，一个是第二界面的
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 10) {
			String info = data.getStringExtra("info");
			et_content_rec.setText(info);
		} 
		
	}
	
}
