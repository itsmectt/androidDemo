package com.ctt.slidemenudemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;


public class user_photo extends AppCompatActivity implements View.OnClickListener {


    private ImageView iv_show;
    private Uri photoUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.left_menu);




        iv_show = (ImageView) this.findViewById(R.id.user_ico);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                SelectHeadTools.startPhotoZoom(this,photoUri, 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data==null)
                    return;
                SelectHeadTools.startPhotoZoom(this, data.getData(), 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data==null)
                    return;
                Bitmap bit = data.getExtras().getParcelable("data");
                iv_show.setImageBitmap(bit);
                File file = FileTools.getFileByUri(this,photoUri);
                Log.d("File",file.toString());
                break;
        }
    }
}
