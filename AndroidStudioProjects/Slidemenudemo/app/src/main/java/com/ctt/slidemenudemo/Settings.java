package com.ctt.slidemenudemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by apple on 15/12/16.
 */
public class Settings extends Activity{
    //第一个数组
    private String[] msg=new String[]{"总是接收","从不接收","提醒但不通知"};
    private boolean[] msgstate=new boolean[]{true, false, false};
    //第二个数组
    private String[] spo=new String[]{"每天","每周六","每周日","从不"};
    private boolean[] spostate=new boolean[]{true, false, false, false};
    private RadioOnClick radioOnClick= new RadioOnClick(1);
    boolean msgClicked,spoClicked=false;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        TextView question= (TextView) findViewById(R.id.question);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出的信息文本框
                Intent intent = new Intent(Settings.this, Textques.class);
                Settings.this.startActivity(intent);
            }
        });

        TextView message= (TextView) findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否接受信息推送1.总是接受2.从不3.提醒但不通知
                msgClicked=true;
                spoClicked=false;
                AlertDialog ad =new AlertDialog.Builder(Settings.this).setTitle("接收消息模式")
                        .setSingleChoiceItems(msg,radioOnClick.getIndex(),radioOnClick).create();
                ad.show();
            }
        });
        TextView update= (TextView) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Settings.this,"已经为最新版本", Toast.LENGTH_LONG).show();
            }
        });
        TextView time= (TextView) findViewById(R.id.spotime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单选每天、周六、周日、不提醒
                spoClicked=true;
                msgClicked=false;
                AlertDialog add =new AlertDialog.Builder(Settings.this).setTitle("运动提醒")
                        .setSingleChoiceItems(spo,radioOnClick.getIndex(),radioOnClick).create();
                add.show();
            }
        });
        TextView hw= (TextView) findViewById(R.id.height_weight);
        hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, height_weight.class);
                Settings.this.startActivity(intent);
            }
        });
    }
    class RadioOnClick implements DialogInterface.OnClickListener{
        private int index;

        public RadioOnClick(int index){
            this.index = index;
        }
        public void setIndex(int index){
            this.index=index;
        }
        public int getIndex(){
            return index;
        }

        public void onClick(DialogInterface dialog, int whichButton){
            setIndex(whichButton);
            if (msgClicked==true) {
                    Toast.makeText(Settings.this, "已选择:" + msg[index], Toast.LENGTH_LONG).show();
                }
            if (spoClicked==true) {
                Toast.makeText(Settings.this, "将在" + spo[index] + "给您进行提醒", Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }
}
