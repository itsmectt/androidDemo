package com.ctt.slidemenudemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("NewApi")
public class MainActivity extends Activity{
	
	private ImageButton imageButton;
	private ListView menuListView;
	private Switch s1,s2;

	public void onCreate(Bundle savedInstanceState) {	    
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SlidingMenu sm=new SlidingMenu(this);
		sm.setMode(SlidingMenu.LEFT);
		sm.setBehindOffsetRes(R.dimen.activity_horizontal_margin);
		sm.setFadeEnabled(true);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.toggle();
		sm.setBehindScrollScale(0.0f);
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		sm.setMenu(R.layout.left_menu);	
		
List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 1; i++) {
			Map<String, Object> item1;
			Map<String, Object> item2;
			Map<String, Object> item3;
			item1 = new HashMap<String, Object>();
			item1.put("item", "打开GPS定位");
			data.add(item1);
			item2 = new HashMap<String, Object>();
			item2.put("item", "运动时屏幕常亮");
			data.add(item2);
			item3 = new HashMap<String, Object>();
			item3.put("item", "通用设置");
			data.add(item3);

		}

		menuListView = (ListView) findViewById(R.id.menu_listview);
		menuListView.setAdapter(new SimpleAdapter(this, data,
				R.layout.menu_text, new String[]{"item"},
				new int[]{R.id.menu_text}));
		menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public  void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				if (position==2) {

					Intent intent = new Intent(MainActivity.this, Settings.class);
					MainActivity.this.startActivity(intent);
				}
			}
		});
	 imageButton=(ImageButton)this.findViewById(R.id.menu_imgbtn);
     imageButton.setOnClickListener(new OnClickListener() {
		 @Override
		 public void onClick(View v) {
			 // TODO Auto-generated method stub

		 }
	 });

	 //switch开关事件
	/*	s1= (Switch) findViewById(R.id.switch1);
		s2= (Switch) findViewById(R.id.switch2);
		s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				if (isChecked) {

				}
			}
		});*/

     //进入“地图浏览功能”
     TextView txtViewRegister=(TextView)findViewById(R.id.textview1);
     txtViewRegister.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub			
			Intent intent=new Intent(MainActivity.this,Main_Location.class);
			MainActivity.this.startActivity(intent);
		}
	}); 
     
     TextView txtViewRegister1=(TextView)findViewById(R.id.textview2);
     txtViewRegister1.setOnClickListener(new View.OnClickListener() {

		 @Override
		 public void onClick(View v) {
			 // TODO Auto-generated method stub
			 Intent intent = new Intent(MainActivity.this, sportstrack.class);
			 MainActivity.this.startActivity(intent);
		 }
	 });
     
     TextView txtViewRegister2=(TextView)findViewById(R.id.textview3);
     txtViewRegister2.setOnClickListener(new View.OnClickListener() {

		 @Override
		 public void onClick(View v) {
			 // TODO Auto-generated method stub
			 Intent intent = new Intent(MainActivity.this, historyrecord.class);
			 MainActivity.this.startActivity(intent);
		 }
	 });
     
     TextView txtViewRegister3 = (TextView) findViewById(R.id.textview4);
		txtViewRegister3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, lishijilu.class);
				MainActivity.this.startActivity(intent);
			}
		});

		//foot-tab
		ImageButton icon_map= (ImageButton) findViewById(R.id.icon_map);
		icon_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,Main_Location.class);
				MainActivity.this.startActivity(intent);
			}
		});
		ImageButton icon_track= (ImageButton) findViewById(R.id.icon_track);
		 icon_track.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 Intent intent=new Intent(MainActivity.this,sportstrack.class);
				 MainActivity.this.startActivity(intent);
			 }
		 });
		ImageButton icon_record= (ImageButton) findViewById(R.id.icon_record);
		icon_record.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, historyrecord.class);
				MainActivity.this.startActivity(intent);
			}
		});
		ImageButton icon_step= (ImageButton) findViewById(R.id.icon_step);
		icon_step.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, lishijilu.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}
}
	
     

