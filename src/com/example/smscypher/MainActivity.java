package com.example.smscypher;

import java.util.ArrayList;

import com.example.listview.Adapter_contact;
import com.example.ways.JustTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	}
	
	public void click1(View v){
		Intent intent = new Intent(this,SenderActivity.class);
		startActivity(intent);
	}
	
	public void click2(View v) {
		Intent intent = new Intent(this,ReceiverActivity.class);
		startActivity(intent);
	}
	
	public void click3(View v){
		Intent intent = new Intent(this,CreateKeyActivity.class);
		startActivity(intent);
	}
	
	public void click4(View v){
		Toast.makeText(getApplicationContext(), "正在生成中，请稍后。。。", 0).show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				final JustTest test = new JustTest();
				final String filePath = Environment.getExternalStorageDirectory()+"/Test/";
			    final String fileName = "数据源.txt";
			    final ArrayList<String> testArray = test.getArr();
				for(int i=0;i<testArray.size();i++){
					test.writeTxtToFile(testArray.get(i), filePath, fileName);
				}
			}
		}).start();
        Toast.makeText(getApplicationContext(), "已保存到私有目录下", Toast.LENGTH_LONG).show();
	}
}
