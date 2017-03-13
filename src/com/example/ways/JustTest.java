package com.example.ways;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import android.util.Log;

public class JustTest {
	
	public ArrayList<String> getArr(){
		ArrayList<String> arr = new ArrayList<String>();
		for(int i=0;i<1000;i++){
			int x=11+(int)(Math.random()*20);
			arr.add(getRandomJianHan(x));
		}
		return arr;
	}
	
	private String getRandomJianHan(int len){
        String ret="";
          for(int i=0;i<len;i++){
              String str = null;
              int hightPos, lowPos; // 定义高低位
              Random random = new Random();
              hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
              lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
              byte[] b = new byte[2];
              b[0] = (new Integer(hightPos).byteValue());
              b[1] = (new Integer(lowPos).byteValue());
              try{
                  str = new String(b, "GBk"); //转成中文
              }
              catch (UnsupportedEncodingException ex){
                  ex.printStackTrace();
              }
               ret+=str;
          }
      return ret;
    }
	
	// 将字符串写入到文本文件中
	public void writeTxtToFile(String strcontent, String filePath, String fileName) {
	    //生成文件夹之后，再生成文件，不然会出错
	    makeFilePath(filePath, fileName);
	     
	    String strFilePath = filePath+fileName;
	    System.out.println("地址："+strFilePath);
	    // 每次写入时，都换行写
	    String strContent = strcontent + "\r\n";
	    try {
	        File file = new File(strFilePath);
	        if (!file.exists()) {
	            Log.d("TestFile", "Create the file:" + strFilePath);
	            file.getParentFile().mkdirs();
	            file.createNewFile();
	        }
	        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
	        raf.seek(file.length());
	        raf.write(strContent.getBytes());
	        raf.close();
	    } catch (Exception e) {
	        Log.e("TestFile", "Error on write File:" + e);
	    }
	}
	 
	// 生成文件
	public File makeFilePath(String filePath, String fileName) {
	    File file = null;
	    makeRootDirectory(filePath);
	    try {
	        file = new File(filePath + fileName);
	        if (!file.exists()) {
	            file.createNewFile();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return file;
	}
	 
	// 生成文件夹
	public static void makeRootDirectory(String filePath) {
	    File file = null;
	    try {
	        file = new File(filePath);
	        if (!file.exists()) {
	            file.mkdir();
	        }
	    } catch (Exception e) {
	        Log.i("error:", e+"");
	    }
	}

//	String sdPath = Environment.getExternalStorageDirectory() + "";
//	File file3 = new File(sdPath + "/test");
//	public void deleteAllFile(String path) {
//		File file = new File(path);
//		if (!file.exists()) {
//			return;
//		}
//		if (file.isFile()) {
//			file.delete();
//		} else if (file.isDirectory()) {
//			String[] tempList = file.list();
//			File temp = null;
//			for (int i = 0; i < tempList.length; i++) {
//				if (path.endsWith(File.separator)) {
//					temp = new File(path + tempList);
//				} else {
//					temp = new File(path + File.separator + tempList);
//				}
//				if (temp.isFile() && temp.toString().endsWith(".txt")) {//
//					temp.delete();
//				}
//				if (temp.isDirectory()) {
//					// 先删除文件夹里面的文件
//					deleteAllFile(path + "/" + tempList);
//				}
//			}
//		}
//	}
}
