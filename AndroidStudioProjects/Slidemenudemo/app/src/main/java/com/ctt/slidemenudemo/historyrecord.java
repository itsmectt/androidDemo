package com.ctt.slidemenudemo;

import com.baidu.platform.comapi.map.i;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
 
public class historyrecord extends Activity {
     
    private static final String[] m1={"跑步","走路","游泳","瑜伽","有氧操"}; 
    private static final String[] m2={"10-20","30-40","40-60","60-120","120以上"}; 
    private static final String[] m3={"200","500","800","1000","1000以上"};
    private static final String[] m4={"600kj/小时","174kj/小时","379kj/小时","114kj/小时","341kj/小时"};
    private TextView view1,view2,view3,view4;
    private Spinner spinner1,spinner2,spinner3;
    private ArrayAdapter<String> adapter1,adapter2,adapter3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historyrecord);
         
        view1 = (TextView) findViewById(R.id.spinnerText);
        spinner1 = (Spinner) findViewById(R.id.Spinner01);     
        
        view2 = (TextView) findViewById(R.id.spinnerText2);
        spinner2 = (Spinner) findViewById(R.id.Spinner02);
        
        view3 = (TextView) findViewById(R.id.spinnerText3);
        spinner3 = (Spinner) findViewById(R.id.Spinner03);
        
        view4=(TextView)findViewById(R.id.calorie);
        //将可选内容与ArrayAdapter连接起来
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m1);   
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m2);  
        adapter3= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m3);
        //设置下拉列表的风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         
        //将adapter 添加到spinner中
        spinner1.setAdapter(adapter1);  
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        //添加事件Spinner事件监听  
        spinner1.setOnItemSelectedListener(new SpinnerSelectedListenerone()); 
        spinner2.setOnItemSelectedListener(new SpinnerSelectedListenertwo());
        spinner3.setOnItemSelectedListener(new SpinnerSelectedListenerthree());
        //设置默认值
        spinner1.setVisibility(View.VISIBLE);   
        spinner2.setVisibility(View.VISIBLE);
        spinner3.setVisibility(View.VISIBLE);
        
        
        
        
         
    }
     
    //使用数组形式操作
    class SpinnerSelectedListenerone implements OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
        	
                 view1.setText("你进行了："+m1[arg2]+"这项运动"); 
                 view4.setText("本次消耗热量"+m4[arg2]);
                 view4.setTextColor(Color.rgb(255,33,33 ));
        }          
              
        public void onNothingSelected(AdapterView<?> arg0) {        }                
    }
  public class SpinnerSelectedListenertwo implements OnItemSelectedListener {

    	@Override
    	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
    			long arg3) {
    		// TODO Auto-generated method stub
    		view2.setText("你运动了："+m2[arg2]+"mins");
    	}

    	@Override
    	public void onNothingSelected(AdapterView<?> arg0) {
    		// TODO Auto-generated method stub

    	}
  

    }
  class SpinnerSelectedListenerthree implements OnItemSelectedListener{
      @Override
      public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
      	
               view3.setText("你运动了："+m3[arg2]+"米");                                                       
      }          
            
      public void onNothingSelected(AdapterView<?> arg0) {        }                
  }
}