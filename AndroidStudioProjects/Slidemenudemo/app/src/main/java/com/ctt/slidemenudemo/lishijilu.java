package com.ctt.slidemenudemo;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



/**
 * @since 2015.09.12
 * @author apple
 * function 实现对加速度传感器的操作来实现记步的功能
 * @version 1.0
 *
 * TEST
 * 测试过程中实现了预期的目标，但是很灵敏，前后左右移动就计数，是否需要修改参数来调节实际的模型  range？验证发现就是
 * 修改range的初始值会影响手机记步的灵敏度！！！
 */
public class lishijilu extends Activity {
	private SensorManager accSensorManager;
	private int step = 0;   //步数
	private double oriValue;  //原始数
	private double lstValue;  //上次数
	private double curValue;  //当前数

	private boolean motiveState = true;  //运动状态
	private boolean processState = false;  //运行状态

	private TextView stepText = null;
	private Button ctlBtn = null;
	private Button pause = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lishijilu);

		//调用传感器
		accSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		stepText = (TextView) findViewById(R.id.step);
		ctlBtn = (Button) findViewById(R.id.controlBtn);
		pause= (Button) findViewById(R.id.pause);

		//按键监听，即当检测到按下按钮时触发activity
		ctlBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//初始状态
				step = 0;
				stepText.setText("0");

				if (processState == true) {

					ctlBtn.setText("开始");
					processState = false;
				} else {
					ctlBtn.setText("停止");
					processState = true;

				}

			}
		});
		pause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (motiveState==true){
					String curStep=String.valueOf(step);
					stepText.setText(curStep);
					motiveState=false;
					onPause();
					pause.setText("继续");
				}else{
					pause.setText("暂停");
					motiveState=true;
					onResume();
				}


			}
		});
	}

	//给传感器添加侦听器
private SensorEventListener sel=new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
				float[] values = event.values;
				//设定一个精度范围,测试验证发现修改精度值影响模拟的效果，即前后手机的幅度、速度之类的会影响记步的效果
				double range=3;
				//计算当前加速向量的模
				curValue=magnitude(values[0], values[1],values[2]);
				//向上加速状态
				if (motiveState==true)
				{
					if (curValue>=lstValue)
					{
						lstValue=curValue;
					}
					else
					{
						if (Math.abs(curValue - lstValue) >range)
						{
							//检测到一次峰值
							oriValue=curValue;
							motiveState=false;
						}
					}
				}
				/**
				 * 记步原理就是，人走路类似一上一下，加速度也是一上一下的两个峰值，两个峰值就是算作一步！
				 * 人行走有多个加速度，onSensorChanged方法中，把三个方向上的加速度看作一个整体，用个三维向量表示
				 * 加速度向量的变化量以它的模为指标！
				 */


				//向下加速
				if (motiveState==false)
				{
					if (curValue<=lstValue)
					{
						lstValue=curValue;
					}
					else
					{
						if (Math.abs(curValue - lstValue) > range)
						{
							//检测到一次峰值
							oriValue=curValue;
							if (processState==true)
							{
								//步数加一
								step+=1;
								String stepNum=String.valueOf(step);
								if (processState==true)
								{
									//更新读数
									stepText.setText(stepNum);
								}
							}
							motiveState=true;
						}
					}
				}
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int i) {

		}
	};

	@Override
	protected void onResume() {

		//加速度传感器注册监听
		accSensorManager.registerListener(
				 sel,accSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_UI
		);
		//三个参数是：监听器SensorListener对象，传感器类型，频度

		super.onResume();
	}

	@Override
	protected void onPause() {

		//取消注册监听器
		accSensorManager.unregisterListener(sel);

		super.onPause();
	}
	private double magnitude(float x, float y, float z) {
		double magnitude=0;
		magnitude=Math.sqrt(x*x+y*y+z*z);
		return magnitude;
	}

}
