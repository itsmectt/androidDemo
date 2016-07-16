package com.ctt.slidemenudemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.ctt.slidemenudemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Main_Location extends Activity {
	//一大堆定义
	
	MapView mView=null;
	LocationClient locClient;
	public MyLocationListener mylistener=new MyLocationListener();
	private LocationMode currentMode;
	BitmapDescriptor currentMarker;//用户自定义定位图标
	BaiduMap baiduMap;
	
	private Timer timer =new Timer();
	private TimerTask task;
	List<LatLng> pointstwo=new ArrayList<LatLng>();
	LatLng p1;
	LatLng p2;
	boolean isfirstloc=true;//是否首次定位

	//设置多线程
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			if (msg.what==1) {
				pointstwo.add(p1);
				pointstwo.add(p2);
				OverlayOptions ooDot=new DotOptions().center(p2).radius(6)
						.color(0xAAFF0000);
				baiduMap.addOverlay(ooDot);
				OverlayOptions ooPolyline = new PolylineOptions().width(4)
						.color(0xAAFF0000).points(pointstwo);
				baiduMap.addOverlay(ooPolyline);
				p1=p2;
				locClient.requestLocation();
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());//初始化context信息
		setContentView(R.layout.checkmap);	
		//Log.d("points", "wwww");
		currentMode=LocationMode.Battery_Saving;//budong		
		mView=(MapView)findViewById(R.id.bmapView);
		baiduMap=mView.getMap();
		//开启定位图层
		baiduMap.setMyLocationEnabled(true);
		baiduMap.setTrafficEnabled(true);
		//设置缩放级别
		baiduMap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().zoom(17).build()));
		
		
		//定位初始化
		locClient=new LocationClient(this);
		locClient.registerLocationListener(mylistener);
		LocationClientOption option=new LocationClientOption();
		option.setOpenGps(true);  //打开GPS
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll");
		option.setScanSpan(1000);
		locClient.setLocOption(option);
		locClient.start();
		
	}
	
	//定位监听函数
	public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location==null||mView==null)
				return;
		MyLocationData locData=new MyLocationData.Builder()
		.accuracy(location.getRadius())
		.direction(100).latitude(location.getLatitude())
		.longitude(location.getLongitude()).build();
		//以上这一长串设置开发者获取方向信息，顺时针0-360
		baiduMap.setMyLocationData(locData);
		if (isfirstloc) {
			isfirstloc=false;
			p1=p2=new LatLng(location.getLatitude(), location.getLongitude());
			MapStatusUpdate u=MapStatusUpdateFactory.newLatLng(p2);
			baiduMap.animateMapStatus(u);
		}else {
			p2 = new LatLng(location.getLatitude(), location.getLongitude());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p2);
			OverlayOptions ooDot = new DotOptions().center(p2).radius(6)
					.color(0xAAFF0000);
			baiduMap.addOverlay(ooDot);
			baiduMap.animateMapStatus(u);
		}
		}
		public void onReceivePoi(BDLocation poiLocation){
			
		}
	}
	protected void onDestroy(){
		locClient.stop();
		baiduMap.setMyLocationEnabled(false);
		mView.onDestroy();
		mView=null;
		pauseTimer();
		super.onDestroy();
		
	}
	protected void onResume(){
		super.onResume();
		mView.onResume();
		startTimer();
	}
	protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mView.onPause();  
        } 
	
	
	public void startTimer() {
		if (timer == null) {
			timer = new Timer();
		}
		if (task == null) {
			task = new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub

					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);

				}
			};
		}
		if (timer != null && task != null) {
			timer.schedule(task, 3000, 1000);
		}
	}

	public void pauseTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (task != null) {
			task.cancel();
			task = null;
		}
	}

}
