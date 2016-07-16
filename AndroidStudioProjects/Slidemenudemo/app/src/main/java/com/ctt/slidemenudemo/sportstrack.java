package com.ctt.slidemenudemo;




import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class sportstrack extends Activity {
	// int count=-0;
	MapView mMapView = null;
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	// double distance=0;

	BaiduMap mBaiduMap;
	private Timer timer = new Timer();
	private TimerTask task;
	// List<LatLng> points = new ArrayList<LatLng>();
	List<LatLng> pointstwo = new ArrayList<LatLng>();
	LatLng p1;
	LatLng p2;
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	//开始运动button

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			// 调用相机回调接口由于MainActivity已经实现了回调接口，所以MainActivity.this即可

			if (msg.what == 1) {
				// if(points.size()>3){
				// pointstwo.add((LatLng)points.get(points.size()-3));
				// pointstwo.add((LatLng)points.get(points.size()-2));
				// pointstwo.add((LatLng)points.get(points.size()-1));
				// }else{
				// pointstwo.add((LatLng)points.get(count));
				// }
				// Log.d("points",""+points.get(points.size()-3).latitude);
				// Log.d("points",""+points.get(points.size()-2).latitude);
				// Log.d("points",""+points.get(points.size()-1).latitude);
				pointstwo.add(p1);
				pointstwo.add(p2);
				// distance+=DistanceUtil. getDistance(p1, p2);
				// LatLng llDot = new LatLng(p2.latitude,p2.longitude);
			//	try{
				OverlayOptions ooDot = new DotOptions().center(p2).radius(6)
						.color(0xAAFF0000);
				mBaiduMap.addOverlay(ooDot);

				OverlayOptions ooPolyline = new PolylineOptions().width(4)
						.color(0xAAFF0000).points(pointstwo);
				mBaiduMap.addOverlay(ooPolyline);
				p1 = p2;
				mLocClient.requestLocation();
			//	}catch(Exception e){
				//	e.printStackTrace();
			//	}

			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		/*
		 * forbid lock screen
		 */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.sportstrack);

		//button
		final Button button= (Button) findViewById(R.id.track);
		final boolean[] btnState = {true};
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btnState[0] ==true) {
					button.setText("轨迹记录中");
					btnState[0] =false;
				}else {
					button.setText("开始运动");
					btnState[0] =true;
				}
			}
		});
		
		// 获取地图控件引用
		Log.d("points", "wwww");

				

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);

		// 开启交通图
		mBaiduMap.setTrafficEnabled(true);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().zoom(17).build()));// 设置缩放级别
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		

	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				// p1=new LatLng(39.93923, 116.357428);
				p1 = p2 = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p2);
				// count++;
				// points.add(p2);
				// pointstwo.add(ll);
				mBaiduMap.animateMapStatus(u);
			} else {
				// count++;
				p2 = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p2);
				// points.add(p2);
				Log.d("points", "wwww");
				OverlayOptions ooDot = new DotOptions().center(p2).radius(6)
						.color(0xAAFF0000);
				mBaiduMap.addOverlay(ooDot);

				mBaiduMap.animateMapStatus(u);

			}

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
		startTimer();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		pauseTimer();
		super.onDestroy();
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
