package com.ctt.slidemenudemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by apple on 15/12/17.
 */
public class height_weight extends Activity {
    EditText height,weight;
    SharedPreferences pref;
    SharedPreferences.Editor ed;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heightweight);

        height= (EditText) findViewById(R.id.edit_height);
        weight= (EditText) findViewById(R.id.edit_weight);
        pref=getSharedPreferences("UserInfo", MODE_PRIVATE);
        ed=pref.edit();
        String h2=pref.getString("edit_height","");
        String w2=pref.getString("edit_weight","");
        height.setText(h2);
        weight.setText(w2);


        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button:
                        String h=height.getText().toString().trim();
                        String w=weight.getText().toString().trim();
                        if("".equals(h)||"".equals(w)){
                            Toast.makeText(height_weight.this,"请填写完整信息",Toast.LENGTH_LONG).show();
                        }else
                        {
                            ed.putString("edit_height", h);
                            ed.putString("edit_weight", w);
                            ed.commit();
                            Toast.makeText(height_weight.this,"保存成功",Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        break;
                }
            }

        });
    }
}
